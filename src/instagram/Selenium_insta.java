package instagram;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Selenium_insta {

	// WebDriver
	private static WebDriver driver;

	// Properties
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	public static final String WEB_DRIVER_PATH = "C:/chromedriver.exe";

	// 크롤링 할 URL
	private String base_url;

	public Selenium_insta() {
		super();

		// System Property SetUp
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

		// Driver SetUp
		driver = new ChromeDriver();
		base_url = "https://www.instagram.com/";
	}

	public static void main(String[] args) throws InterruptedException {
		String id = null, pw = null, tag = null;
		int count = 10;
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("인스타그램 ID : ");
			id = sc.nextLine();
			System.out.println("인스타그램 비밀번호 : ");
			pw = sc.nextLine();
			System.out.println("검색어 : ");
			tag = sc.nextLine();
			System.out.println("확인할 피드 개수 :");
			count = sc.nextInt();
			sc.close();
		} catch (Exception e) {
			System.out.println("뭘 잘못한거야? 다시 실행해~~");
		}
		Selenium_insta selTest = new Selenium_insta();
		selTest.login(id, pw);
		selTest.find(tag);
		selTest.like(count);
		driver.close();
	}

	public void login(String id, String pw) throws InterruptedException {
		driver.get(base_url);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement toLogin;
		toLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id=\"react-root\"]/section/main/article/div[2]/div[2]/p/a")));
		toLogin.click();
		//페이스북으로 로그인 클릭
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"react-root\"]/section/main/div/article/div/div[1]/div/form/div[6]/button"))).click();;
		WebElement putId = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"email\"]")));
		putId.sendKeys(id);
		driver.findElement(
				By.xpath("//*[@id=\"pass\"]"))
				.sendKeys(pw);
		;
		Thread.sleep(1000);
		driver.findElement(
				By.xpath("//*[@id=\"loginbutton\"]")).click();
		try{wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div/div/div[3]/button[2]")))
				.click();
		}catch(Exception e) {
			driver.close();
		}

	}

	public void find(String hashtag) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/nav/div[2]/div/div/div[2]/input"))
				.sendKeys(hashtag);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id=\"react-root\"]/section/nav/div[2]/div/div/div[2]/div[2]/div[2]/div/a[1]"))).click();
		// 최근 게시글 중 첫번째꺼 선택
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id=\"react-root\"]/section/main/article/div[2]/div/div[1]/div[1]/a/div[1]/div[2]")))
				.click();

	}

	public void like(int count) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		for (int i = 0; i < count; i++) {
			WebElement like = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("/html/body/div[3]/div[2]/div/article/div[2]/section[1]/span[1]/button/span")));
			String aria_label = like.getAttribute("aria-label");
			System.out.println(aria_label);
			if (aria_label.equals("좋아요")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("/html/body/div[3]/div[2]/div/article/div[2]/section[1]/span[1]/button"))).click();
			}
			driver.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/a[2]")).click();
			Thread.sleep(1000);
		}
	}
}
