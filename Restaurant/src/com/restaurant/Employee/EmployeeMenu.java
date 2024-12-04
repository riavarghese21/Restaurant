package com.restaurant.Employee;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;


public class EmployeeMenu {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeMenu window = new EmployeeMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EmployeeMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Employee Signed In");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLookAndFeel();
		
		viewReservationsButton();
		viewReviewsButton();
		viewOrdersButton();
		viewStatisticsButton();	
	    logoutButton();

		
			JLabel welcomeEmployeeLabel = new JLabel("Welcome Employee!");
			welcomeEmployeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
			welcomeEmployeeLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
			welcomeEmployeeLabel.setBounds(143, 6, 172, 21);
			frame.getContentPane().add(welcomeEmployeeLabel);
			
	}
	
	private void viewReservationsButton() {
		frame.getContentPane().setLayout(null);
		JButton viewReservationsButton = new JButton("View Reservations");
		viewReservationsButton.setBounds(81, 60, 310, 29);
		frame.getContentPane().add(viewReservationsButton);
		viewReservationsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToViewReservations();
			}
		});
		
	}
	
	public void goToViewReservations() {
	    frame.dispose();
	    ViewReservations VRes = new ViewReservations(); 
	    VRes.frame.setVisible(true);
	}
	
	
	
	private void viewReviewsButton() {
		frame.getContentPane().setLayout(null);
		JButton viewReviewsButton = new JButton("View Reviews");
		viewReviewsButton.setBounds(81, 101, 310, 29);
		frame.getContentPane().add(viewReviewsButton);
		viewReviewsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToViewReviews();
			}
		});
		
	}
	
	public void goToViewReviews() {
	    frame.dispose();
	    ViewReviews VR = new ViewReviews(); 
	    VR.getFrame().setVisible(true);
	}


	
	
	private void viewOrdersButton() {
		frame.getContentPane().setLayout(null);
		JButton viewOrdersButton = new JButton("View Orders");
		viewOrdersButton.setBounds(81, 142, 310, 29);
		frame.getContentPane().add(viewOrdersButton);
		viewOrdersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToViewOrders();
			}
		});
		
	}
	
	public void goToViewOrders() {
	    frame.dispose();
	    ViewOrders VO = new ViewOrders(); 
	    VO.getFrame().setVisible(true);
	}

	
	private void viewStatisticsButton() {
		frame.getContentPane().setLayout(null);
		JButton viewStatisticsButton = new JButton("View Statistics");
		viewStatisticsButton.setBounds(81, 183, 310, 29);
		frame.getContentPane().add(viewStatisticsButton);
		viewStatisticsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToViewStatistics();
			}
		});
		
	}
	
	public void goToViewStatistics() {
	    frame.dispose();
	    ViewStatistics VS = new ViewStatistics(); 
	    VS.getFrame().setVisible(true);
	}
	
	public void logoutButton() {
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(81, 215, 310, 29); 
        frame.getContentPane().add(logoutButton);
      
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
	
	
	
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) { }
	}
	
	public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
