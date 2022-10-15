package conf;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import com.google.inject.Inject;

import dao.BidDao;
import ninja.scheduler.Schedule;

@Singleton
public class ScheduledAction {
	
	@Inject
	BidDao bidDao;

	@Schedule(delay = 10, initialDelay = 5, timeUnit = TimeUnit.SECONDS)
	public void closeBids() {
		bidDao.closeBids();
		System.out.println("closeBids running...");
	}
}
