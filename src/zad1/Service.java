/**
 *
 *  @author Karpenko Oleksandr S16934
 *
 */

package zad1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;


import org.json.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;


public class Service {

    final static String APPKEY_WEATHER = "21410f32854cadb3120f29d2e52f5eda";

   private URL url;

   private URLConnection urlConnection;

  private String country_name,city_name, currency_code_foreign;

  private  Currency currency;


  public Service(String country_name) {

        this.country_name = country_name;

        this.currency = Currency.getInstance(getCurrentLocaleByCountryName());
    }


    public String getWeather(String city_name){

        String result = "";

        this.city_name = city_name;

        try{

            url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city_name + "&appid=" + APPKEY_WEATHER);

            urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String data = bufferedReader.readLine();

            bufferedReader.close();

            JSONObject jsonObject = new JSONObject(data);

            for (Map.Entry<String,Object> pair:
                 jsonObject.toMap().entrySet()) {
               result += pair.getKey() + ": " + pair.getValue();
            }

        }catch (Exception ex){ex.printStackTrace();}

        return result;

    }

  public Double getRateFor(String currency_code) {

      currency = Currency.getInstance(getCurrentLocaleByCountryName());

        Double result = 0.0;

        try {

            url = new URL(" https://api.exchangeratesapi.io/latest?base=" + currency.getCurrencyCode());

            urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String data = bufferedReader.readLine();

            bufferedReader.close();

            result = new JSONObject(new JSONObject(data).get("rates").toString())
                    .getDouble(currency_code);

        }catch (Exception ex){ex.printStackTrace();}

      return result;
   }

   public Double getNBPRate(){

        Double result = 0.0;

       currency = Currency.getInstance(getCurrentLocaleByCountryName());

        if (country_name.equalsIgnoreCase("Poland")){

            return 1.0;
        }

        try {

            String url = "http://www.nbp.pl/kursy/kursya.html";
            String url2 = "http://www.nbp.pl/kursy/kursyb.html";

            Document document = Jsoup.connect(url).get();
            Document document2 = Jsoup.connect(url2).get();

            document.append(document2.html());

           int index = document.select("td[class$=\"right\"]")
                               .indexOf(document.select("td[class$=\"right\"]")
                                       .select("td:contains(" + currency.getCurrencyCode() + ")")
                                       .first());

           result = Double.parseDouble(document.
                   select("td[class$=\"right\"]").get(index+1).
                   text().
                   replaceAll(",","."));



        }catch (Exception ex){ex.printStackTrace();}

        return result;
   }

   public Locale getCurrentLocaleByCountryName(){

        Locale locale = null;

       Locale[] locales = Locale.getAvailableLocales();

       for (int i = 0; i < locales.length; i++) {

           if (locales[i].getDisplayCountry(new Locale(Locale.ENGLISH.getLanguage())).equalsIgnoreCase(country_name)){

               locale = locales[i];

               break;
           }

       }

       return locale;
   }

    public void setCity_name(String city_name) {
       this.city_name = city_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCurrency_code_foreign(String currency_code_foreign) {
        this.currency_code_foreign = currency_code_foreign;
    }

    public String getCurrency_code_foreign() {
        return currency_code_foreign;
    }

    public Currency getCurrency() {
        return currency;
    }

}
