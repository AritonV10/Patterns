import java.util.*;

interface Display{
    
    void display();
}

interface Observer{
    
    void update(float temperature, float humidity, float pressure);
}
interface Subject{
    
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
class Temperature implements Observer{
    
    private float temperature;
    private Subject subject;
    
    public Temperature(Subject subject){
        this.subject = subject;
        subject.registerObserver(this);
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure){
        this.temperature = temperature;
    }
}
class Pressure implements Observer{
    
    private float pressure;
    private Subject subject;
    
    public Pressure(Subject subject){
        this.subject = subject;
        subject.registerObserver(this);
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure){
        this.pressure = pressure;
    }
    
}
class Humidity implements Observer{
    
    private float humidity;
    private Subject subject;
    
    public Humidity(Subject subject){
        this.subject = subject;
        subject.registerObserver(this);
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure){
        this.humidity = humidity;
    }
    
}

class ForecastStatistics implements Observer{
    
    private float temperature;
    private       Subject subject;
    
    public ForecastStatistics(Subject subject){
        this.subject = subject;
        subject.registerObserver(this);
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure){
        if(temperature != this.temperature){
            if(temperature < 75.0)
                display("It's pretty cold outside");
             else if(temperature > 75.0 && temperature < 80.0){
                display("Good weather on the way!");
             else if(temperature > 80){
                display("It's pretty hot outside");
            
             this.temperature = temperature;
        }
    }
    
    public void display(String message){
        System.out.println("Forecast: " + message);
    }
}



class CurrentConditionsStatistics implements Observer, Display{
    
    private float temperature;
    private float humidity;
    private       Subject subject;
    
    public CurrentConditionsStatistics(Subject subject){
        this.subject = subject;
        subject.registerObserver(this);
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure){
        if(temperature != this.temperature)
            System.out.println("Temperature has been updated");
        if(humidity != this.humidity)
            System.out.println("Humidity has been updated");
        
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }
    
    @Override
    public void display(){
        System.out.println("Current conditions: " + this.temperature + " degrees and " + this.humidity + "% humidity");
    }
} 
class WeatherStation implements Subject{
    private float temperature;
    private float humidity;
    private float pressure;
    
    private ArrayList<Observer> observers;
    
    public WeatherStation(){
        observers = new ArrayList<>();
    }
    
    @Override
    public void registerObserver(Observer observerToAdd){
        add(observerToAdd);
    }
    @Override
    public void removeObserver(Observer observerToAdd){
        add(observerToAdd);
    }
    @Override
    public void notifyObservers(){
        notifyHelper();
    }
    
    void setMeasurements(float temperature, float humidity, float pressure){
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObservers();
    }
    private void notifyHelper(){
        for(Observer observer : observers){
            observer.update(this.temperature, this.humidity, this.pressure);
        }
    }
    
    private void add(Observer observerToAdd){
        for(Observer observer : observers){
            if(observer == observerToAdd)
                return;
        }
        observers.add(observerToAdd);
    }
}
public class ObserverPattern{

     public static void main(String []args){
        WeatherStation weatherData = new WeatherStation();
        CurrentConditionsStatistics currentMeasures = new CurrentConditionsStatistics(weatherData);
        ForecastStatistics forecast = new ForecastStatistics(weatherData);
        
        Temperature temperature = new Temperature(weatherData);
        Pressure pressure = new Pressure(weatherData);
        weatherData.setMeasurements(78.0f, 5.0f, 6.1f);
        weatherData.setMeasurements(8.5f, 5.0f, 6.2f);
        
     }
}
