package service;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mercadolibre.config.AppConfig;
import com.mercadolibre.config.WebAppInitializer;
import com.mercadolibre.config.WebConfig;
import com.mercadolibre.enums.WheatherStatusEnum;
import com.mercadolibre.model.Weather;
import com.mercadolibre.service.WeatherService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ServiceTest {
	private final static Logger logger = Logger.getLogger(ServiceTest.class.getName());
	@Autowired
	WeatherService weatherService;
	@Test
	public void testService () {
		try {
			TimeUnit.MINUTES.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Weather weather = weatherService.getWeather(72);
		logger.log(Level.INFO, "Day: " + weather.getDay() + " Status: " + weather.getStatus());
		Assert.assertTrue(weather.getStatus().equals(WheatherStatusEnum.RAIN.toString()));
		
	}
}
