package Odevler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

/**
 * <h1>Otel Yönetim Sistemi</h1>
 * Otel Yönetim Sistemi; oda durumlarına göre rezervasyon,rezervasyon iptali,
 * check in, check out işlemlerini gerçekleştirmektedir.
 * <p>
 * Tarafımdan belirlenen kullanıcılar sisteme giriş yapabilmekte ve oda durumlarının
 * sağlaması gereken durumlara göre işlemlerini yapabilmektedirler.
 * <p>
 * <b>Note:</b> Resepsiyonistin yetkisi misafirden fazladır. Check in check out
 * işlemlerini resepsiyonist gerçekleştirirken misafir gerçekleştiremez.
 *
 * @author Nur Hatipoğlu
 * @version 1.0
 * @since 2014-03-31
 */

/**
 * Resepsiyon girişi için aşağıdaki bilgier ile giriş yapılabilir:
 * Kullanıcı adı : Nisa  , şifre:111
 * Kullanıcı adı : Eda  , şifre:222
 */

/**
 * Müşteri girişi için aşağıdaki bilgier ile giriş yapılabilir:
 * Kullanıcı adı : Nur  , şifre:111
 * Kullanıcı adı : Elif Nil  , şifre:222
 */
public class OtelYonetimTest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String secim2;
        boolean devam=true;
        System.out.println("Otele Hoşgeldiniz!");
        while(devam){
            System.out.println("Resepsiyonist Girişi İçin 1, Misafir Girişi İçin 2 yi tuşlayınız ");
            int secim = scan.nextInt();
            scan.nextLine();
            System.out.println("Adınızı giriniz:");
            String kullaniciadi= scan.nextLine();
            System.out.println("Şifrenizi giriniz:");
            int sifre= scan.nextInt();
            try{
                FileWriter fw = new FileWriter("GirisKayitlari.csv",true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
                pw.println(kullaniciadi+" , "+sifre);
                pw.flush();
                pw.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            switch(secim){
                case 1:
                    Giris resepsiyonGiris = new ResepsiyonGirisi(kullaniciadi,sifre);
                    break;
                case 2:
                    Giris musteriGiris = new MusteriGirisi(kullaniciadi,sifre);
                    break;
            }
            System.out.println("Başka işlem yapmak istiyor musunuz?(e/h)");
            secim2 = scan.next();
            if(secim2.equals("e")) devam = true;
            else devam=false;
        }
        System.out.println("İşleminiz sonlandı.");
    }
}
/**
 *Bu sınıf giriş işlemlerinde kullanılmak için oluşturulmuştur.
 */
abstract class Giris{
    String isim;
    int sifre;

    /**
     * Kullanıcı şifresini ve kullanıcı adını girdiğinde kurucu metot girilen bilgileri bu sınıftaki değişkenlere atar.
     * @param isim Giris kurucu metotunun ilk parametresidir.
     * @param sifre Giris kurucu metotunun ikinci parametresidir.
     */
    public Giris(String isim, int sifre) {
        this.isim = isim;
        this.sifre = sifre;
    }

    /**
     * Bu metotun alt sınıflarda kullanımı mecbur olduğundan abstract olarak oluşturulmuştur.
     * <b>Note:</b>Sadece sisteme kayıtlı kullanıcıların sisteme girebilmesi için alt sınıflar
     * tarafından kullanılacaktır.
     */
    public abstract void kontrol();
}

/*
* Bu sınıf kullanılarak kullanıcıya yetki alanına göre rezervasyon,rezervasyon iptali,check in,check out işlemleri sunulmaktadır.
* Hem resepsiyonist hem misafir kullanıcıya seçenek sunulmak zorunda olduğundan tercih adında soyut metot oluşturulmuştur.
*/
interface Secim{
    void tercih();
}

class ResepsiyonGirisi extends Giris implements Secim{
    Scanner scan = new Scanner(System.in);
    String[] resepsiyonKullanici = {"Nisa","Eda"};
    int[] rsepsiyonSifre = {111,222};
    boolean kontrol=false;

    public ResepsiyonGirisi(String isim, int sifre) {
        super(isim, sifre);
        kontrol();
    }

    /*
    * Bu metot seçilen işlem sonucunda işlemi yaoacak olan sınfa yönlendirmektedir.
    */
    @Override
    public void tercih() {
        if(kontrol){
            System.out.println("Resepsiyonist girişi yaptınız");
            System.out.println("Rezervasyon yapmak için 1\n" +
                    "Rezervasyon iptali için 2\n" +
                    "Check in için 3\n" +
                    "Check out için 4 ü tıklayınız.");
            int secim = scan.nextInt();
            switch(secim){
                case 1:
                    Rez rez=new Rez(secim);
                    rez.rezyap();
                    break;
                case 2:
                    Rez rez2=new Rez(secim);
                    rez2.rezyap();
                    break;
                case 3:
                    Rez rez3=new Rez(secim);
                    rez3.rezyap();
                    break;
                case 4:
                    Rez rez4=new Rez(secim);
                    rez4.rezyap();
                    break;
            }
        }
        else{
            System.out.println("Giriş Yapılamadı.");
        }
    }

    /*Bu metot sisteme sadece kayıtlı kullanıcıların girebilmesini sağlamaktadır.*/
    @Override
    public void kontrol() {
        for(int i=0;i< resepsiyonKullanici.length ;i++){
            if(this.isim.equals(resepsiyonKullanici[i]) && rsepsiyonSifre[i]==this.sifre){
                kontrol =true;
                break;
            }
            else{
                kontrol=false;
            }
        }
        tercih();
    }
}
class MusteriGirisi extends Giris implements Secim{
    Scanner scan = new Scanner(System.in);
    String[] misafirKullanici = {"Nur","Elif Nil"};
    int[] misafirSifre = {111,222};
    boolean kontrol=false;

    public MusteriGirisi(String isim, int sifre) {
        super(isim, sifre);
        kontrol();
    }

    @Override
    public void tercih() {
        if(kontrol){
            System.out.println("Misafir girişi yaptınız");
            System.out.println("Rezervasyon yapmak için 1\n" +
                    "Rezervasyon iptali için 2 yi tıklayınız.");
            int secim = scan.nextInt();
            switch(secim){
                case 1:
                    Rez rez = new Rez(secim);
                    rez.rezyap();
                    break;
                case 2:
                    Rez rez2=new Rez(this.isim);
                    rez2.rezSil();
                    break;
            }
        }
        else{
            System.out.println("Giriş Yapılamadı.");
        }
    }

    @Override
    public void kontrol() {
        for(int i=0;i< misafirKullanici.length ;i++){
            if(this.isim.equals(misafirKullanici[i]) && misafirSifre[i]==this.sifre){
                kontrol =true;
                break;
            }
            else{
                kontrol=false;
            }
        }
        tercih();
    }
}

class Rez{
    Scanner scan = new Scanner(System.in);
    int odaNo,kisiSayisi,secim;
    String adSoyad,gelisTarihi,cikisTarihi;
    PreparedStatement ps = null;

    /**
     * Parametresiz kurucu metotdur.
     */
    public Rez() {
        System.out.println("Yapmak istediğiniz işlem için oda numarası giriniz:");
        odaNo= scan.nextInt();
    }

    /**
     * Kurucu metot kullanıcının seçtiği işlem seçeneğini bu sınıftaki değişkene atar.
     * @param secim Rez kurucu metotunun tek parametresidir.
     */
    public Rez(int secim) {
        this.secim = secim;
        System.out.println("Yapmak istediğiniz işlem için oda numarası giriniz:");
        odaNo= scan.nextInt();
    }

    /**
     * Kurucu metot kullanıcının sisteme girerken kullandığı kullanıcı adını bu sınıftaki değişkene atar.
     * @param adSoyad Rez kurucu metotunun tek parametresidir.
     */
    public Rez(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    /**
     * Bu metot parametre almaz. Kullanıcının tercih ettiği işlemi gerçekleştirir.
     */
    public void rezyap(){
        boolean eger=true;
        boolean guncelleme=true;
        if(secim==1){
            eger=true;
            /**
             * @exception SQLException veri tabanına bağlanırken istisna oldu
             * @see SQLException
             */
            try(Connection baglanti = DriverManager.getConnection("jdbc:sqlite:otel.db");
                Statement statement = baglanti.createStatement();){
                ResultSet rs=statement.executeQuery("SELECT * FROM  OtelKayitlari WHERE OdaNo="+odaNo);
                //eger veri tabanında oda kaydı varsa rezervasyon yapılamaz.
                if(rs.next()){
                    System.out.println("Rezervasyon yapılamadı.");
                    eger=false;
                }
            }catch (SQLException hata){
                hata.printStackTrace();
            }
            //eger sistemde oda kaydı yoksa yeni oda kaydı oluşturulur.
            if(eger){
                bilgiGirisi2();
                try(Connection baglantı = DriverManager.getConnection("jdbc:sqlite:otel.db");){
                    System.out.println("Veri tabanına bağlanıldı.");
                    System.out.println("Rezervasyon yapıldı.");
                    String sorgu = "INSERT INTO OtelKayitlari (OdaNo,OdaDurumu,AdSoyad,KisiSayisi,GelisT,CikisT) VALUES ('"+this.odaNo+"', '"+"rezerve"+"', '"+this.adSoyad+"','"+this.kisiSayisi+"', '"+this.gelisTarihi+"', '"+this.cikisTarihi+"')";
                    Statement statement = baglantı.createStatement();
                    statement.executeUpdate(sorgu);
                }catch(SQLException hata){
                    hata.printStackTrace();
                }
            }

        }
        else if(secim==2){
            eger=true;
            /**
             * @exception SQLException veri tabanına bağlanırken istisna oldu
             * @see SQLException
             */
            try(Connection baglanti = DriverManager.getConnection("jdbc:sqlite:otel.db");
                Statement statement = baglanti.createStatement();){
                ResultSet rs=statement.executeQuery("SELECT * FROM  OtelKayitlari WHERE OdaNo="+this.odaNo);
                /*eger iptal edilmek istenen odano nun oda durumu veri tabanında rezerve dışında check in,check out
                şeklinde kayıtlıysa rez iptal edilemez. Oda Rezerve değilse iptal edilemez mantığı kullanılmıştır.*/
                if(rs.next()){
                    if(!rs.getString("OdaDurumu").equals("rezerve")){
                        System.out.println("İptal edilemedi.");
                        eger=false;
                    }
                }
            }catch (SQLException hata){
                hata.printStackTrace();
            }

            if(eger){
                try(Connection baglantı = DriverManager.getConnection("jdbc:sqlite:otel.db");){
                    System.out.println("Veri tabanına bağlanıldı.");
                    System.out.println("Rezervasyon iptal edildi.");
                    String sorgu = "DELETE FROM OtelKayitlari WHERE OdaNo="+ this.odaNo;
                    Statement statement = baglantı.createStatement();
                    statement.executeUpdate(sorgu);
                }catch(SQLException hata){
                    hata.printStackTrace();
                }
            }
        }
        else if(secim==3){
            eger=true;
            guncelleme=true;
            bilgiGirisi2();
            /**
             * @exception SQLException veri tabanına bağlanırken istisna oldu
             * @see SQLException
             */
            try(Connection baglanti = DriverManager.getConnection("jdbc:sqlite:otel.db");
                Statement statement = baglanti.createStatement();){
                ResultSet rs=statement.executeQuery("SELECT * FROM  OtelKayitlari WHERE OdaNo="+odaNo);
                /*eger check in yapılmak istenen odano nun oda durumu veri tabanında rezerve dışında check in,check out
                şeklinde kayıtlıysa check in yapılamaz.Sadece rezerve olan check in yapabilir mantığı kullanılmıştır.*/
                if(rs.next()){
                    if(!rs.getString("OdaDurumu").equals("rezerve")){
                        System.out.println("Check in yapılamadı.");
                        eger=false;
                    }
                    else if(rs.getString("OdaDurumu").equals("rezerve")){
                        if(rs.getString("AdSoyad").equals(adSoyad)){
                            /*daha önce misafirin sistemde rezerve şeklinde kaydı varsa check in misafir edilebilir.*/
                            System.out.println("Rezerve edilen oda giriş yapmak istiyor.");
                            guncelleme=false; //bu değişkeni kullanarak misafirin rezerve etmiş olduğu odayı check in yapacağız.
                            eger=false;
                        }
                    }
                }
            }catch (SQLException hata){
                hata.printStackTrace();
            }
            //misafir odayı rezerve etmiş ve şimdi check in yapmak istiyorsa tablo güncellenir(update) edilir.
            if(!guncelleme){
                try{
                    Connection baglanti2 = DriverManager.getConnection("jdbc:sqlite:otel.db");
                    System.out.println("Rezerve edilen oda check in yapıldı.");
                    String sorgu= "UPDATE OtelKayitlari SET OdaDurumu='check in' WHERE OdaNo"+"=?"+"AND AdSoyad"+"=?";
                    ps = baglanti2.prepareStatement(sorgu);
                    ps.setInt(1,odaNo);
                    ps.setString(2,adSoyad);
                    ps.executeUpdate();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            //eger sistemde oda kaydı yoksa oluşturulur.
            if(eger){
                try(Connection baglantı = DriverManager.getConnection("jdbc:sqlite:otel.db");){
                    System.out.println("Veri tabanına bağlanıldı.");
                    System.out.println("Check in yapıldı.");
                    String sorgu = "INSERT INTO OtelKayitlari (OdaNo,OdaDurumu,AdSoyad,KisiSayisi,GelisT,CikisT) VALUES ('"+this.odaNo+"', '"+"check in"+"', '"+this.adSoyad+"','"+this.kisiSayisi+"', '"+this.gelisTarihi+"', '"+this.cikisTarihi+"')";
                    Statement statement = baglantı.createStatement();
                    statement.executeUpdate(sorgu);
                }catch(SQLException hata){
                    hata.printStackTrace();
                }
            }

        }
        else if(secim==4){
            eger=true;
            guncelleme=true;
            bilgiGirisi2();
            /**
             * @exception SQLException veri tabanına bağlanırken istisna oldu
             * @see SQLException
             */
            try(Connection baglanti = DriverManager.getConnection("jdbc:sqlite:otel.db");
                Statement statement = baglanti.createStatement();){
                ResultSet rs=statement.executeQuery("SELECT * FROM  OtelKayitlari WHERE OdaNo="+odaNo);
                /*eger check out yapılmak istenen odano nun oda durumu veri tabanında check in dışında rezerve,check out
                şeklinde kayıtlıysa check in yapılamaz.Check in durumundaki oda check out yapılabilir mantığı kullanılmıştır.*/
                if(rs.next()){
                    if(!rs.getString("OdaDurumu").equals("check in")){
                        System.out.println("Check out yapılamadı.");
                        eger=false;
                    }
                    else if(rs.getString("OdaDurumu").equals("check in")){
                        if(rs.getString("AdSoyad").equals(adSoyad)){
                            //daha önce misafirin sistemde check in şeklinde kaydı varsa check out edilebilir.
                            System.out.println("Check in olan misafir check out yapmak istiyor.");
                            guncelleme=false;//bu değişkeni kullanarak check in olan odayı check out yapacağız.
                            eger=false;
                        }
                    }
                }
            }catch (SQLException hata){
                hata.printStackTrace();
            }
            //misafir odada ise(check in) ve şimdi check out yapmak istiyorsa tablo güncellenir(update) edilir.
            if(!guncelleme){
                try{
                    Connection baglanti2 = DriverManager.getConnection("jdbc:sqlite:otel.db");
                    System.out.println("Check in olan oda check out yapıldı.");
                    String sorgu= "UPDATE OtelKayitlari SET OdaDurumu='check out' WHERE OdaNo"+"=?"+"AND AdSoyad"+"=?";
                    ps = baglanti2.prepareStatement(sorgu);
                    ps.setInt(1,odaNo);
                    ps.setString(2,adSoyad);
                    ps.executeUpdate();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            //eger sistemde oda kaydı yoksa oluşturulur.
            if(eger){
                try(Connection baglantı = DriverManager.getConnection("jdbc:sqlite:otel.db");){
                    System.out.println("Veri tabanına bağlanıldı.");
                    System.out.println("Check out yapıldı.");
                    String sorgu = "INSERT INTO OtelKayitlari (OdaNo,OdaDurumu,AdSoyad,KisiSayisi,GelisT,CikisT) VALUES ('"+this.odaNo+"', '"+"check out"+"', '"+this.adSoyad+"','"+this.kisiSayisi+"', '"+this.gelisTarihi+"', '"+this.cikisTarihi+"')";
                    Statement statement = baglantı.createStatement();
                    statement.executeUpdate(sorgu);
                }catch(SQLException hata){
                    hata.printStackTrace();
                }
            }
        }
    }
    /**
     * Bu metotda kullanıcı rezervasyon  iptali istediğinde kullanıcının sisteme girerken kullandığı adı kullanarak
     * kullanıcıdan başka bilgi almaksızın kendi adına kayıtlı olan rez iptal ediliyor. Böylece kullanıcıya kolaylık sağlanıyor.
     */
    public void rezSil(){
        PreparedStatement pr =null;
        ResultSet rs = null;
        boolean eger=true;
        /**
         * @exception SQLException veri tabanına bağlanırken istisna oldu
         * @see SQLException
         */
        try(Connection baglanti = DriverManager.getConnection("jdbc:sqlite:otel.db");
            Statement statement = baglanti.createStatement();){
            ps = baglanti.prepareStatement("SELECT * FROM  OtelKayitlari WHERE AdSoyad="+"=?");
            ps.setString(1,adSoyad);
            rs = ps.executeQuery();
            /*eger iptal edilmek istenen odano nun oda durumu veri tabanında rezerve dışında check in,check out
            şeklinde kayıtlıysa rez iptal edilemez. Oda rezerve ise iptal edilebilir mantığı kullanılmıştır.*/
            if(rs.next()){
                if(!rs.getString("OdaDurumu").equals("rezerve")){
                    System.out.println("İptal edilemedi.");
                    eger=false;
                }
            }
        }catch (SQLException hata){
            hata.printStackTrace();
        }
        //eger oda durumu rezerve ise rezervasyonun iptali gerçekleştirilir ve veri tabanından kayıt silinir.
        if(eger){
            try(Connection baglantı = DriverManager.getConnection("jdbc:sqlite:otel.db");){
                System.out.println("Rezervasyon iptal edildi.");
                String sorgu2 = "DELETE FROM OtelKayitlari WHERE AdSoyad="+"=?";
                pr = (PreparedStatement) baglantı.prepareStatement(sorgu2);
                pr.setString(1, adSoyad);
                pr.executeUpdate();

            }catch(SQLException hata){
                hata.printStackTrace();
            }
        }
    }

    /**
     * Bu metot Kullanıcıdan oda işlemleri için alınan bilgilerden oluşmaktadır.
     */
    public void bilgiGirisi2(){
        scan.nextLine();
        System.out.println("Oda sahibinin adı ve soy adını giriniz:");
        adSoyad = scan.nextLine();
        System.out.println("Konaklayacak kişi sayısını giriniz:");
        kisiSayisi= scan.nextInt();
        System.out.println("Geliş tarihini giriniz:");
        gelisTarihi = scan.next();
        System.out.println("Çıkış tarihini giriniz:");
        cikisTarihi = scan.next();
    }
}

