import java.awt.*;
import javax.swing.*;

public class OS_2014136142 extends JFrame{
	
	private static final long serialVersionUID = 1L;
	//static int n = 0;

	JPanel printTimer = new JPanel();
	JTextArea setting;
	Semaphore space = new Semaphore(8);
	
	JLabel getIn;
	JLabel getOut;
	JLabel timerLabel;
	JLabel timer;

	
	OS_2014136142(){
		setTitle("Semaphore1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		timerLabel = new JLabel();
		timerLabel.setFont(new Font("Gothic", Font.ITALIC, 20));
		TimerThread runnable = new TimerThread(timerLabel);
		
		timer = new JLabel();
		Thread th = new Thread(runnable);
		printTimer.add(timerLabel);
		
		setting();
		panel p = new panel();
		p.add(setting);
		add(p);
		add(printTimer, BorderLayout.SOUTH);
		// ��ġ ����
			
		setSize(720, 570);
		setVisible(true);
		th.start();
	}
	
	/*
	 * ��� ���� ���� ���ڸ� ���
	 */
	void setting(){
		
		setting = new JTextArea(100, 64){
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g){
				g.setFont(new Font("Arial", Font.BOLD, 30));
				g.drawString(space.nrFull+"", 270, 81);			// nrFull�� ���� ���
				
				g.setFont(new Font("Arial", Font.BOLD, 30));
				g.drawString(space.nrEmpty+"", 430, 81);		// nrEmpty�� ���� ���
				
				g.setFont(new Font("Arial", Font.BOLD, 30));
				g.drawString(space.mutexP+"", 270, 152);		// mutexP�� ���� ���
				
				g.setFont(new Font("Arial", Font.BOLD, 30));
				g.drawString(space.mutexC+"", 430, 152);		// mutexC�� ���� ���
				
				g.setFont(new Font("Arial", Font.ITALIC, 20));
				g.drawString("IN : " + space.in + ", ", 80, 480);// IN�� ���� ���
				
				g.setFont(new Font("Arial", Font.ITALIC, 20));
				g.drawString("OUT : " + space.out, 150, 480);	// OUT�� ���� ���
				
				if(space.buffer[0] != 0){						// ������ 0������ ���� ��� �ִٸ�
					g.setFont(new Font("Arial", Font.BOLD, 30));
					g.drawString("M"+space.buffer[0], 365, 235);// ������ 0���� ���� ���
				}
				if(space.buffer[1] != 0){						// ������ 1������ ���� ��� �ִٸ�
					g.setFont(new Font("Arial", Font.BOLD, 30));
					g.drawString("M"+space.buffer[1], 425, 290);// ������ 1���� ���� ���	
				}
				if(space.buffer[2] != 0){						// ������ 2������ ���� ��� �ִٸ�
					g.setFont(new Font("Arial", Font.BOLD, 30));
					g.drawString("M"+space.buffer[2], 427, 370);// ������ 2���� ���� ���	
				}
				if(space.buffer[3] != 0){						// ������ 3������ ���� ��� �ִٸ�
					g.setFont(new Font("Arial", Font.BOLD, 30));
					g.drawString("M"+space.buffer[3], 365, 430);// ������ 3���� ���� ���	
				}
				if(space.buffer[4] != 0){						// ������ 4������ ���� ��� �ִٸ�
					g.setFont(new Font("Arial", Font.BOLD, 30));
					g.drawString("M"+space.buffer[4], 295, 430);// ������ 4���� ���� ���	
				}
				if(space.buffer[5] != 0){						// ������ 5������ ���� ��� �ִٸ�
					g.setFont(new Font("Arial", Font.BOLD, 30));
					g.drawString("M"+space.buffer[5], 240, 370);// ������ 5���� ���� ���
				}
				if(space.buffer[6] != 0){						// ������ 6������ ���� ��� �ִٸ�
					g.setFont(new Font("Arial", Font.BOLD, 30));
					g.drawString("M"+space.buffer[6], 240, 290);// ������ 6���� ���� ���
				}
				if(space.buffer[7] != 0){						// ������ 7������ ���� ��� �ִٸ�
					g.setFont(new Font("Arial", Font.BOLD, 30));
					g.drawString("M"+space.buffer[7], 295, 235);// ������ 7���� ���� ���	
				}
				
				
				if(space.fullIsZero){							// ���۰� ���� ����
					g.setFont(new Font("Arial", Font.BOLD, 30));
					g.drawString("P"+space.buffer[space.count], 180, 155);	
				
				}
				if(space.emptyIsZero){							// ���ۿ� ������ ������
					g.setFont(new Font("Arial", Font.BOLD, 30));
					g.drawString("C"+space.buffer[space.count], 490, 155);	
				
				}
				setOpaque(false);
				super.paintComponent(g);
			}
		};
	}
	
	/*
	 * ȭ�鿡 ���������� ����� �Ǵ� �׸��� ���
	 */
	class panel extends JPanel{
		
		private static final long serialVersionUID = 1L;
		
		ImageIcon icon = new ImageIcon("C:\\Users\\OWNER\\workspace\\2014136142\\Semaphore.jpg");
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(icon.getImage(), 0, 0, null);
		}
	}
	
	/*
	 * TImerThread Ŭ����
	 * ȭ�鿡 ��µǴ� �ð��� �����Ѵ�.
	 */
	class TimerThread extends Thread{
		
		Person p1 = new Person(space, "M1", 5000, 1);		// ������ p1
		Person p2 = new Person(space, "M2", 7000, 1);		// ������ p1
		Person c1 = new Person(space, "C1", 15000, 0);		// �Һ��� c1
		Person c2 = new Person(space, "C2", 12000, 0);		// �Һ��� c1
		
		
		JLabel timerLabel;						// Ÿ�̸� ���� ��µ� ���̺�
		public TimerThread(JLabel timerLabel){	// ������
			this.timerLabel = timerLabel;		// ���̺� ������Ʈ�� �޾Ƽ� ���
		}
		
		// ������ �ڵ�
		// run() �� �����ϸ� ������ ����
		public void run(){
			int n=0;
			while(true){	// ���� ����
				timerLabel.setText("Timer : "+Integer.toString(n));	// Ÿ�̸� ���� ���̺� ���
				try{
					Thread.sleep(1000);
					if(n == 1)	p1.start();
					if(n == 7)	p2.start();
					if(n == 4)	c1.start();
					if(n == 10)	c2.start();
					n++;
					if(n == 41)	System.exit(0);
					
				}catch(InterruptedException e){
					return;
				}
			}
		}
	}
		
	
	
	class Semaphore
	{
		int mutexP = 1;						// ��������� mutexP. �ʱⰪ�� 1
		int mutexC = 1;						// ��������� mutexC. �ʱⰪ�� 1
		int nrEmpty = 8;					// ������ ���� �ִ� ����. �ʱⰪ�� 8
		int nrFull = 0;						// ���ۿ��� �� �ִ� ����. �ʱⰪ�� 0
		int count = 0;						// �迭 
		int[] buffer = new int[8];			// ���ۿ� ������ ��� 8ĭ¥�� �迭
		int in = 0;							// ���ۿ� �߰� ������ ��ġ���� ������.
		int out = 0;						// ���ۿ��� ��� ������ ��ġ���� ���´�.
		
		boolean imWaiting = false;			// V ������ ������ �� ����ϱ� ���� ����
		boolean emptyIsZero = false;		// empty�� 0�̹Ƿ� ���ť�� ���
		boolean fullIsZero = false;			// full�� 0�̹Ƿ� ���ť�� ���
		
		/*
		 * Semaphore Ŭ������ �������̸� �迭�� ������ buffer�� ���� 0���� �ʱ�ȭ���ش�.
		 */
		public Semaphore(int num)
		{
			for(int i=0; i<8; i++)
				buffer[i] = 0;
		}
		
		/*
		 * P ������ ������ �Ѵ�.
		 * �Է°����� ���� n�� 0���� ū ��� -1�� �ϸ�
		 * 0�� ��� ��� ť�� �����ϰ� �ȴ�.
		 */
		public synchronized int semGet(int n) throws InterruptedException		// P �Լ�
		{
			while(n == 0){
				if(nrEmpty == 0)	emptyIsZero = true;
				if(nrFull == 0)		fullIsZero = true;
				imWaiting = true;
				repaint();
				this.wait();
			}
			n--;
			return n;
		}
		
		/*
		 * V ������ ������ �Ѵ�.
		 * ��� ť�� �������� �����尡 �ִٸ� �����ְ�
		 * 
		 */
		public synchronized int semRelease(int n) throws InterruptedException	// V �Լ�
		{
			if(imWaiting){
				emptyIsZero = false;
				fullIsZero = false;
				imWaiting = false;	
			}
			n++;
			repaint();
			notifyAll();
			return n;
		}
	}
	
	
	class Person extends Thread
	{
		private int waitTime;				// ��� �ð�
		private Semaphore space;			// �������� Ŭ������ ��ü�� ���
		private int porc;					// Producer�ΰ� Consumer�ΰ��� ����
		
		/*
		 * Person Ŭ������ ���� ������
		 */
		public Person(Semaphore sp, String name, int wt, int p)
		{
			this.setName(name);
			this.space = sp;
			waitTime = wt;
			porc = p;
		}
		
		/*
		 * Person Ŭ������ �۾��� ����.
		 * Producer�ΰ� Consumer�ΰ��� ����
		 * run �Լ��� �۾� ������ �ٸ���.
		 */
		public void run()
		{
			try{
				while(true)
				{
					if(porc == 1)
					{
						space.mutexP = space.semGet(space.mutexP);		// P(mutexP)
						space.nrEmpty = space.semGet(space.nrEmpty);	// P(nrEmpty)
						repaint();
						
						space.buffer[space.in] = space.count+1;
						space.count++;
						space.in = (space.in + 1) % 8;
						
						Thread.sleep(500);
						
						space.nrFull = space.semRelease(space.nrFull);	// V(nrFull)
						space.mutexP = space.semRelease(space.mutexP);	// V(mutexP)
						repaint();
						Thread.sleep(waitTime);
					}
					if(porc == 0)
					{
						space.mutexC = space.semGet(space.mutexC);
						space.nrFull = space.semGet(space.nrFull);
						repaint();
						
						space.buffer[space.out] = 0;
						space.out = (space.out + 1) % 8;
						
						Thread.sleep(500);
						
						space.nrEmpty = space.semRelease(space.nrEmpty);
						space.mutexC = space.semRelease(space.mutexC);
						repaint();
						Thread.sleep(waitTime);
					}
				}
			}catch(InterruptedException e){e.printStackTrace();}
			
		}
	}

	
	public static void main(String[] args) {
		new OS_2014136142();
	}

}
