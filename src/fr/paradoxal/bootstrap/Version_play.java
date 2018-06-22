package fr.paradoxal.bootstrap;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.sun.awt.AWTUtilities;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.colored.SColoredButton;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;

@SuppressWarnings("serial")
public class Version_play extends JFrame implements SwingerEventListener {

	private static JLabel title = new JLabel("Version de jeux");
	
	private static SColoredButton premium = new SColoredButton(Swinger.getTransparentWhite(100));
	private static SColoredButton crack = new SColoredButton(Swinger.getTransparentWhite(100));
	
	private static JLabel info = new JLabel("-> Version Premium");
	private static JLabel info_crack = new JLabel("-> Version Crack");
	
	public Version_play() {
		this.setLayout(null);
		
		this.setTitle("Choix Version");
		this.setSize(250, 250);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.getContentPane().setBackground(Color.gray);
		this.setIconImage(Swinger.getResource("Logo.png"));
		
		title.setForeground(Color.white);
		title.setFont(title.getFont().deriveFont(19F));
		title.setBounds(50,1,150,50);
		this.add(title);
		
		premium.setForeground(Color.BLACK);
		premium.setBackground(Color.GRAY);
		premium.addEventListener(this);
		premium.setBounds(25, 50, 50, 25);
		this.add(premium);
		
		crack.setForeground(Color.white);
		crack.addEventListener(this);
		crack.setBounds(25, 100, 50, 25);
		this.add(crack);
		
		info.setForeground(Color.white);
		info.setFont(info.getFont().deriveFont(15F));
		info.setBounds(80, 35, 150, 50);
		this.add(info);
		
		info_crack.setForeground(Color.white);
		info_crack.setFont(info.getFont().deriveFont(15F));
		info_crack.setBounds(80, 85, 150, 50);
		this.add(info_crack);
		
		AWTUtilities.setWindowOpacity(this, 0.0F);
		
		this.setVisible(true);
		
		Animator.fadeInFrame(this,Animator.FAST);
		
	}

	@Override
	public void onEvent(SwingerEvent arg0) {
		if(arg0.getSource()==premium) 
		{
			this.setVisible(false);
			try {
				Bootstrap.doUpdate_pre();
				Bootstrap.premiun.createNewFile();
			} catch (Exception e) {
				Bootstrap.crashReporter.catchError(e, "Impossible de mettre à jour le launcher !");
			}
			Bootstrap.phase_two();

		}
		else if(arg0.getSource()==crack) 
		{
			this.setVisible(false);
			try {
				Bootstrap.doUpdate_crack();
				Bootstrap.crack.createNewFile();
			} catch (Exception e) {
				Bootstrap.crashReporter.catchError(e, "Impossible de mettre à jour le launcher !");
			}
			Bootstrap.phase_two();
		}		
	}	
}
