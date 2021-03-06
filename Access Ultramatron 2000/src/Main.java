import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import javax.jws.Oneway;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{

    private static Connection connection;
    private static Statement statement;
    private static boolean isInit = false;

    public static void main(String[] args) throws SQLException, FileNotFoundException
    {
        //commaSeperatedWorker();
        //commaSeperatedEczaci();
        //commaSeperatedDoctor();
        //ilacFiyat();
        //commaSeperatedRecete();
        //commaSeperatedReceteDetail();
        //otherFiyat();
        //satis();
        //satisDetay();
        stokDoldur();
    }

    private static void stokDoldur()
    {
        ArrayList<String> liste = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            ArrayList<Integer> selectedProduct = new ArrayList<>();
            for (int j = 0; j < Utils.getRandomInt(1900, 2100); j++)
            {
                int productNo = 0;
                do
                {
                    productNo = Utils.getRandomInt(1, 6848);
                } while (selectedProduct.contains(productNo));
                selectedProduct.add(productNo);
                int stok = Utils.getRandomInt(50, 101);
                String randomDate = String.format("20%02d-%02d-%02d 0:0:0", Utils.getRandomInt(21, 25),
                        Utils.getRandomInt(1, 13), Utils.getRandomInt(1, 28));
                liste.add(String.format("%d;%d;%d;%s", productNo, i + 1, stok, randomDate));
            }
        }
        for (int i = 0; i < liste.size(); i++)
        {
            liste.set(i, (i + 1) + ";" + liste.get(i));
        }
        writeToFile("stok", liste);

    }

    private static void satisDetay()
    {
        ArrayList<String> detay = new ArrayList<>();
        for (int i = 0; i < 2560; i++)
        {
            double probability = Utils.getRandom(1);
            int iter = 0;
            if (probability <= 0.8)
            {
                iter = Utils.getRandomInt(1, 5);
            } else if (probability > 0.8 && probability <= 0.95)
            {
                iter = Utils.getRandomInt(5, 10);
            } else
            {
                iter = Utils.getRandomInt(10, 15);
            }
            //
            for (int j = 0; j < iter; j++)
            {
                int qua = 1;
                if (Utils.getRandom(1) < 0.05)
                {
                    qua = Utils.getRandomInt(2, 5);
                }
                int product = Utils.getRandomInt(1, 6848);
                detay.add(String.format("%d;%d;%d;%d", (i + 1), (j + 1), product, qua));
            }
        }
        for (int i = 0; i < detay.size(); i++)
        {
            detay.set(i, (i + 1) + ";" + detay.get(i));
        }
        writeToFile("satisDetay", detay);
    }

    private static void satis()
    {
        ArrayList<String> satislar = new ArrayList<>();
        for (int i = 0; i < 2560; i++)
        {
            //1265 reteçe
            int human = Utils.getRandomInt(1, 751); // 1 ile 750 arası bir insan yani vergi mükkelefi
            String randomDate = String.format("20%02d-%02d-%02d %d:%02d:%02d", Utils.getRandomInt(12, 20),
                    Utils.getRandomInt(1, 13), Utils.getRandomInt(1, 28), Utils.getRandomInt(9, 17), Utils.getRandomInt(0, 60), Utils.getRandomInt(0, 60));
            int eczaci = Utils.getRandomInt(1, 51); // 1 ile 30 arası bir doctor
            double probabilty = Utils.getRandom(1);
            satislar.add(String.format("%d;%d;%d;%s", (i + 1), human, eczaci, randomDate));
        }
        writeToFile("satis", satislar);
    }

    private static void otherFiyat() throws FileNotFoundException
    {
        ArrayList<String> product = loadDataFromTxt("other.txt");
        for (int i = 0; i < product.size(); i++)
        {
            double rand = Utils.getRandom(1);
            int price = 0;
            int dec = Utils.getRandomInt(0, 100);
            if (rand <= 0.05)
            {
                // high price
                price = Utils.getRandomInt(75, 250);
            } else if (rand > 0.05 && rand <= 0.15)
            {
                // mid price
                price = Utils.getRandomInt(25, 75);
            } else
            {
                //cheap
                price = Utils.getRandomInt(5, 25);
            }
            product.set(i, product.get(i) + String.format(";%d,%02d", price, dec));
        }
        for (String str : product)
        {
            System.out.println(str);
        }
        //writeToFile("others",product);
    }

    private static void commaSeperatedReceteDetail()
    {
        ArrayList<String> detay = new ArrayList<>();
        for (int i = 0; i < 1265; i++)
        {
            double probability = Utils.getRandom(1);
            int iter = 0;
            if (probability <= 0.8)
            {
                iter = Utils.getRandomInt(1, 4); //1,2,3
            } else if (probability > 0.8 && probability <= 0.95)
            {
                iter = Utils.getRandomInt(4, 8); //4,5,6,7
            } else
            {
                iter = Utils.getRandomInt(8, 11); //8,9,10
            }
            //
            for (int j = 0; j < iter; j++)
            {
                int qua = 1;
                if (Utils.getRandom(1) < 0.05)
                {
                    qua = Utils.getRandomInt(2, 5);
                }
                int drug = Utils.getRandomInt(1, 6799);
                detay.add(String.format("%d;%d;%d;%d", (i + 1), (j + 1), drug, qua));
            }
        }
        for (int i = 0; i < detay.size(); i++)
        {
            detay.set(i, (i + 1) + ";" + detay.get(i));
        }
        writeToFile("detay", detay);
    }

    private static void commaSeperatedRecete()
    {
        ArrayList<String> receteler = new ArrayList<>();
        for (int i = 0; i < 1265; i++)
        {
            //1265 reteçe
            int human = Utils.getRandomInt(1, 751); // 1 ile 750 arası bir insan yani vergi mükkelefi
            String randomDate = String.format("20%02d-%02d-%02d %d:%02d:%02d", Utils.getRandomInt(9, 20),
                    Utils.getRandomInt(1, 13), Utils.getRandomInt(1, 28), Utils.getRandomInt(9, 17), Utils.getRandomInt(0, 60), Utils.getRandomInt(0, 60));
            int doctor = Utils.getRandomInt(1, 31); // 1 ile 30 arası bir doctor
            int receteType = 1;
            double probabilty = Utils.getRandom(1);
            if (probabilty <= 0.07)
            {
                receteType = Utils.getRandomInt(2, 6); // 2 ile 5 arası.
            }
            receteler.add(String.format("%d;%d;%d;%d;%s", (i + 1), human, doctor, receteType, randomDate));
        }
        writeToFile("receteler", receteler);
    }

    private static void writeToFile(String fileName, ArrayList<String> tableOfContext)
    {
        try
        {
            File myObj = new File("outputText/" + fileName + ".txt");
            if (myObj.createNewFile())
            {
                System.out.println("Dosya: '" + myObj.getName() + "' ismiyle oluşturuldu.");
                System.out.println("Yazma işlemi başlatılıyor.");
                FileWriter fileWriter = new FileWriter("outputText/" + fileName + ".txt");
                for (String row : tableOfContext)
                {
                    fileWriter.write(row + "\n");
                }
                fileWriter.close();
            } else
            {
                System.out.println(fileName + " isimli bir dosya zaten mevcut. İşlem durduruluyor.");
            }
        }
        catch (IOException e)
        {
            System.out.println("Bir şey oldu."); // :)
            e.printStackTrace();
        }
    }

    private static void ilacFiyat() throws FileNotFoundException
    {
        ArrayList<String> product = loadDataFromTxt("Urun");
        for (int i = 0; i < product.size(); i++)
        {
            double rand = Utils.getRandom(1);
            int price = 0;
            int dec = Utils.getRandomInt(0, 100);
            if (rand <= 0.05)
            {
                // high price
                price = Utils.getRandomInt(75, 250);
            } else if (rand > 0.05 && rand <= 0.15)
            {
                // mid price
                price = Utils.getRandomInt(15, 75);
            } else
            {
                //cheap
                price = Utils.getRandomInt(2, 15);
            }
            product.set(i, product.get(i) + String.format(";%d,%02d", price, dec));
        }

        for (String str : product)
        {
            System.out.println(str);
        }
    }

    private static void showTable(String tableName) throws SQLException
    {
        ResultSet rs = statement.executeQuery(String.format("Select * from [%s];", tableName));
        ResultSetMetaData msm = rs.getMetaData();
        while (rs.next())
        {
            for (int i = 1; i <= msm.getColumnCount(); i++)
            {
                System.out.print(rs.getString(i) + " ");
            }
            System.out.println();
        }
    }

    private static void showSehir() throws SQLException
    {
        ResultSet rs = statement.executeQuery("Select * from [ATC];");
        ResultSetMetaData msm = rs.getMetaData();
        while (rs.next())
        {
            for (int i = 1; i <= msm.getColumnCount(); i++)
            {
                System.out.print(rs.getString(i) + " ");
            }
            System.out.println();
        }
    }

    private static void initDatabase() throws SQLException
    {
        //String url = "jdbc:ucanaccess://C:/VTYS/VTYS.accdb";
        String url = "jdbc:ucanaccess://ACCESSDB.accdb";
        //
        Connection connection = DriverManager.getConnection(url);
        statement = connection.createStatement();
    }

    private static ArrayList<String> loadDataFromTxt(String FileName) throws FileNotFoundException
    {
        ArrayList<String> rows = new ArrayList<>();
        String path = "QueryFolder/" + FileName; //res/data.txt //default
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine())
        {
            //Scan everyline and append to arrayList
            rows.add(scanner.nextLine());
        }
        return rows;
    }

    private static void vatandas()
    {
        ArrayList<String> insert = new ArrayList<>();
        for (int i = 1; i <= 750; i++)
        {
            if (Utils.getRandom(1) < 0.96)
            {
                //TC
                ArrayList<String> usedTC = new ArrayList<>();
                String tc = createTC();
                while (usedTC.contains(tc))
                {
                    tc = createTC();
                }
                //
                String guvence = "";
                if (Utils.getRandom(1) <= 0.8)
                {
                    guvence = "SGK";
                } else
                {
                    guvence = "Yeşil Kart";
                }
                insert.add(String.format("Insert into [TCVatandas] ([TCno], [Guvence], [insanNo]) values ('%s', '%s', %d);", tc, guvence, i));
            } else
            {
                int ikamet = Utils.getRandomInt(100000, 999999);
                int taahut = Utils.getRandomInt(100000, 999999);
                insert.add(String.format("Insert into [Yabanci] ([ikametgahNo], [taahhutnameNo], [insanNo]) values ('%06d', '%06d', %d);", ikamet, taahut, i));
            }
        }
        for (String row : insert)
        {
            System.out.println(row);
        }
    }

    private static void createInserHasta() throws FileNotFoundException
    {
        ArrayList<String> isimCinsiyet = loadDataFromTxt("isim.txt");
        ArrayList<String> soyisimList = loadDataFromTxt("soyisim.txt");
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = 0; i < 1000; i++)
        {
            nums.add(i + 1); //rip
        }
        ArrayList<String> usedTel = new ArrayList<>();
        //DONE
        for (int i = 0; i < 750; i++)
        {
            String tel = createTel();
            while (usedTel.contains(tel))
            {
                tel = createTel();
            }
            //
            int adIndex = Rand.getInt(isimCinsiyet.size());
            String[] div = isimCinsiyet.get(adIndex).split(";");
            String ad = div[0];
            String cinsiyet = div[1];
            isimCinsiyet.remove(adIndex);
            //
            int soyadIndex = Rand.getInt(soyisimList.size());
            String soyad = soyisimList.get(soyadIndex);
            soyisimList.remove(soyadIndex);
            //

            int adrindex = Rand.getInt(nums.size() - 100);
            int adr = nums.get(adrindex);
            nums.remove(adrindex);
            //
            String date = String.format("%04d-%02d-%02d 0:0:0", Rand.getInt(1940, 2005), Rand.getInt(1, 13), Rand.getInt(1, 28));
            //
            System.out.printf("Insert into [Insan] ([Ad], [Soyad], [TelNo], [Cinsiyet], [DogumTarih], [AdresNo]) values ('%s', '%s', '%s', '%s', '%s', %d);\n", ad, soyad, tel, cinsiyet, date, adr);
        }
    }

    private static void createInsertAdres() throws FileNotFoundException
    {
        ArrayList<String> queries = loadDataFromTxt("adress.txt");
        ArrayList<String> mahalle = new ArrayList<>();
        ArrayList<String> cadde = new ArrayList<>();
        ArrayList<String> zipcode = new ArrayList<>();
        for (String row : queries)
        {
            String[] arr = row.split(";");
            mahalle.add(arr[0].trim());
            cadde.add(arr[1].trim());
            zipcode.add(arr[2].trim());
        }
        int limit = mahalle.size();
        for (int i = 0; i < 1200; i++)
        {
            int ilceNo = Rand.getInt(1, 971);
            int randomAdr = Rand.getInt(limit);
            String no = "" + Rand.getInt(76) + "/" + Rand.getInt(35);
            System.out.printf("Insert into [Adres] ([ilce_no], [mahalle], [cadde], [no], [postakodu]) values (%s, '%s', '%s', '%s', %s);\n",
                    ilceNo, mahalle.get(randomAdr), cadde.get(randomAdr), no, zipcode.get(randomAdr));
        }
    }

    private static void executeSQL(String queryPath, String tableName) throws SQLException, FileNotFoundException
    {
        initDatabase();
        ArrayList<String> queries = loadDataFromTxt(queryPath);
        for (String query : queries)
        {
            //System.out.println(query);
            try
            {
                statement.executeUpdate(query);
            }
            catch (Exception ex)
            {
                System.out.println("ERR: " + query);
            }
        }
        showTable(tableName);
    }

    private static void addBirthdate()
    {
        for (int i = 1; i <= 751; i++)
        {
            String date = String.format("%04d-%02d-%02d 0:0:0", Rand.getInt(1940, 2005), Rand.getInt(1, 13), Rand.getInt(1, 28));
            System.out.printf("Update [Hasta] set [DogumTarih] = '%s' where [id] = %d;\n", date, i);
        }
    }

    private static void shuffle(ArrayList<String> list)
    {
        for (int i = 0; i < list.size(); i++)
        {
            int r1 = Utils.getRandomInt(list.size());
            int r2 = Utils.getRandomInt(list.size());
            String temp = list.get(r1);
            list.set(r1, list.get(r2));
            list.set(r2, temp);
        }
    }

    private static void divIsim() throws FileNotFoundException
    {
        ArrayList<String> queries = loadDataFromTxt("ISIM.txt");
        for (String row : queries)
        {
            String[] liste = row.split(";");
            if (!liste[2].equals("U"))
            {
                System.out.println(liste[1] + ";" + liste[2]);
            }
        }
    }

    private static String createTel()
    {
        return String.format("05%02d%03d%04d", Rand.getInt(30, 40), Rand.getInt(100, 980), Rand.getInt(0, 9999));
    }

    private static String createTC()
    {
        int seed = Utils.getRandomInt(100000000, 999999999);
        String tc = Integer.toString(seed);
        int odd = 0;
        int even = 0;
        for (int i = 0; i < tc.length(); i++)
        {
            if (i % 2 == 0)
            {
                //odd
                odd += Character.getNumericValue(tc.charAt(i));
            } else
            {
                //even
                even += Character.getNumericValue(tc.charAt(i));
            }
        }
        odd = (odd * 7 - even) % 10;
        tc += Integer.toString(odd);
        int sum = 0;
        for (int i = 0; i < tc.length(); i++)
        {
            sum += Character.getNumericValue(tc.charAt(i));
        }
        tc += Integer.toString(sum % 10);
        return tc;
    }

    private static void commaSeperatedEczaci()
    {
        ArrayList<Integer> distinct = new ArrayList<>();
        ArrayList<String> array = new ArrayList<>();
        //
        for (int i = 0; i < 50; i++)
        {
            int diplomaNo = Utils.getRandomInt(100000, 999999);
            int vatandasNo = -1;
            do
            {
                vatandasNo = Utils.getRandomInt(1, 720);
            } while (distinct.contains(vatandasNo));
            distinct.add(vatandasNo);
            //
            array.add(String.format("%d;%6d", vatandasNo, diplomaNo));
        }
        shuffle(array);
        for (int i = 0; i < 50; i++)
        {
            System.out.println((i + 1) + ";" + array.get(i));
        }
    }

    private static void commaSeperatedWorker()
    {
        ArrayList<String> array = new ArrayList<>();
        int index = 1;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                array.add(String.format("%d;%d;%4d-%02d-%02d 0:0:0;%d,%03d", index++, j + 1, Utils.getRandomInt(2000, 2014),
                        Utils.getRandomInt(1, 13), Utils.getRandomInt(1, 28), Utils.getRandomInt(4000, 8500), Utils.getRandomInt(0, 1000)));
            }
        }
        shuffle(array);
        for (int i = 0; i < array.size(); i++)
        {
            System.out.println((i + 1) + ";" + array.get(i));
        }
    }

    private static void commaSeperatedDoctor()
    {
        ArrayList<Integer> distinct = new ArrayList<>();
        ArrayList<String> array = new ArrayList<>();
        //
        for (int i = 0; i < 30; i++)
        {
            int alanNo = Utils.getRandomInt(1, 42);
            int vatandasNo = -1;
            do
            {
                vatandasNo = Utils.getRandomInt(650, 720);
            } while (distinct.contains(vatandasNo));
            distinct.add(vatandasNo);
            //
            array.add(String.format("%d;%d", vatandasNo, alanNo));
        }
        shuffle(array);
        for (int i = 0; i < 30; i++)
        {
            System.out.println((i + 1) + ";" + array.get(i));
        }
    }

}