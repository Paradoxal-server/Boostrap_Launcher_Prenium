package fr.paradoxal.bootstrap;

import static fr.theshark34.swinger.Swinger.getResource;
import static fr.theshark34.swinger.Swinger.getTransparentWhite;

import java.awt.Color;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.sun.awt.AWTUtilities;

import fr.theshark34.openlauncherlib.LanguageManager;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ClasspathConstructor;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.SplashScreen;
import fr.theshark34.openlauncherlib.util.explorer.ExploredDirectory;
import fr.theshark34.openlauncherlib.util.explorer.Explorer;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.colored.SColoredBar;
public class Bootstrap {

	public static final File P_B_DIR = new File(GameDirGenerator.createGameDir("paradoxal"),"Launcher");
	public static CrashReporter crashReporter = new CrashReporter("Paradoxal Bootstrap",P_B_DIR);
	
	private static SplashScreen splash;
	private static SColoredBar bar;
	private static Thread barthread;
	
	public static File crack = new File(P_B_DIR,"crack.txt");
	public static File premiun = new File(P_B_DIR,"premiun.txt");
	
	private static JLabel infolabel = new JLabel("Bienvenu ! ",SwingConstants.CENTER);
	@SuppressWarnings("unused")
	private static JFrame frame;
	public static int i = 0;
	public static void main(String[] args) {
		LanguageManager.setLang(LanguageManager.FRENCH);
		
		Swinger.setResourcePath("/fr/paradoxal/bootstrap/ressources/");
		
		displayStatic();
		System.out.println(crack.exists());
		System.out.println(premiun.exists());
		if(!crack.exists() && !premiun.exists()) 
		{
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			frame = new Version_play();
		}
		else
		{
			i=1;
		}
		if(i!=0) {
			if(crack.exists())
			{
				System.out.println("Version Crack");
				try {
					doUpdate_crack();
				}catch (Exception e) {
					crashReporter.catchError(e, "Impossible de mettre à jour le launcher !");
				}
				phase_two();
			}
			else if(premiun.exists()) 
			{
				System.out.println("Version Premiun");
				try {
					doUpdate_pre();
				}catch (Exception e) {
					crashReporter.catchError(e, "Impossible de mettre à jour le launcher !");
				}
				phase_two();
			}
		}
		
	}
	
	public static void phase_two() {
		try {
			launcLauncher();
		}catch (LaunchException e) {
			crashReporter.catchError(e, "Imposible de lancer Paradoxal");
		}
	}
	
	private static void displayStatic() {
		splash = new SplashScreen("Pardoxal", getResource("splash.png"));
		splash.setLayout(null);
		splash.setIconImage(getResource("Logo.png"));
		infolabel.setForeground(Color.white);
		infolabel.setFont(infolabel.getFont().deriveFont(19F));
		infolabel.setBounds(200,320,120,20);
		splash.add(infolabel);
		
		AWTUtilities.setWindowOpacity(splash, 0.0F);
		
		bar = new SColoredBar(getTransparentWhite(100),getTransparentWhite(175));
		bar.setBounds(10,350,476,20);
		splash.add(bar);
		
		splash.setVisible(true);
		Animator.fadeInFrame(splash,Animator.FAST);
	}		
	
	public static void doUpdate_pre() throws Exception{
		SUpdate su = new SUpdate(InfoBootstrap.S_UPDATE_PRENIUM, P_B_DIR);
		su.addApplication(new FileDeleter());
		barthread = new Thread() {
			private int val = 0;
			private int max = 0;
			@Override
			public void run() {
				while(!this.isInterrupted())
				{
					if(BarAPI.getNumberOfFileToDownload() ==0 ) {
						Bootstrap.setInfoText("Vérification des fichiers");
						Bootstrap.setBoundInfo(38, 320, 400, 30);
						continue;
					}
					
					val = (int) (BarAPI.getNumberOfTotalDownloadedBytes()/1000);
					max = (int) (BarAPI.getNumberOfTotalBytesToDownload()/1000);
					bar.setValue(val);
					bar.setMaximum(max);

					setInfoText("Téléchargement des fichiers "+BarAPI.getNumberOfDownloadedFiles()+" / "+BarAPI.getNumberOfFileToDownload()+" - "+Swinger.percentage(val, max)+"%");
					setBoundInfo(38, 320, 400, 30);
				}
			}
		};
		barthread.start();
		
		su.start();
		barthread.interrupt();		
	}
	
	public static void doUpdate_crack() throws Exception{
		SUpdate su = new SUpdate(InfoBootstrap.S_UPDATE_CRACK, P_B_DIR);
		barthread = new Thread() {
			private int val = 0;
			private int max = 0;
			@Override
			public void run() {
				while(!this.isInterrupted())
				{
					if(BarAPI.getNumberOfFileToDownload() ==0 ) {
						Bootstrap.setInfoText("Vérification des fichiers");
						Bootstrap.setBoundInfo(38, 320, 400, 30);
						continue;
					}
					
					val = (int) (BarAPI.getNumberOfTotalDownloadedBytes()/1000);
					max = (int) (BarAPI.getNumberOfTotalBytesToDownload()/1000);
					bar.setValue(val);
					bar.setMaximum(max);

					setInfoText("Téléchargement des fichiers "+BarAPI.getNumberOfDownloadedFiles()+" / "+BarAPI.getNumberOfFileToDownload()+" - "+Swinger.percentage(val, max)+"%");
					setBoundInfo(38, 320, 400, 30);
				}
			}
		};
		barthread.start();
		
		su.start();
		barthread.interrupt();		
	}
	
	static void launcLauncher() throws LaunchException{
		
		
		ClasspathConstructor constructor = new ClasspathConstructor();
		ExploredDirectory gameDir = Explorer.dir(P_B_DIR);
		constructor.add(gameDir.sub("Libs").allRecursive().files().match("^(.*\\.((jar)$))*$"));
		constructor.add(gameDir.get("launcher.jar"));
		
		ExternalLaunchProfile profile = new ExternalLaunchProfile("fr.paradoxal.launcher.Launcher", constructor.make());
		ExternalLauncher launcher = new ExternalLauncher(profile);
		System.out.println(profile.getClassPath());
		
		Process p = launcher.launch();
		
		splash.setVisible(false);
		try {
			p.waitFor();
		} catch (InterruptedException ignored) {
		}
		System.exit(0);
	}
	
	public static void setInfoText(String text) {
		infolabel.setText(text);
	}
	
	public static void setBoundInfo(int x, int y,int width, int height)
	{
		infolabel.setBounds(x, y, width, height);
	}
}
