package com.restaurant.Customer;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.restaurant.SignInPage;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CustomerSignedIn {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerSignedIn window = new CustomerSignedIn();
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
	public CustomerSignedIn() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Customer Signed In");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLookAndFeel();
		
		orderOnlineButton();
		makeReservationButton();
		viewHistoryButton();
		purchaseGiftCardButton();
		leaveAReviewButton();	
		accountSettingsButton();
	    logoutButton();
	    
	    JLabel lblNewLabel = new JLabel("Welcome!");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(145, 19, 310, 16);
		frame.getContentPane().add(lblNewLabel);
	}
	
	
	
	public void orderOnlineButton() {
        frame.getContentPane().setLayout(null);
        JButton orderOnlineButton = new JButton("Order Online");
        orderOnlineButton.setBounds(145, 59, 310, 29); 
        frame.getContentPane().add(orderOnlineButton);
        orderOnlineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToOrderFood();
            }
        });
    }

    public void goToOrderFood() {
        frame.dispose(); 
        OrderFood orderFoodPage = new OrderFood();
        orderFoodPage.frame.setVisible(true); 
    }
    
    public void makeReservationButton() {
        JButton makeReservationButton = new JButton("Make a Reservation");
        makeReservationButton.setBounds(145, 100, 310, 29); 
        frame.getContentPane().add(makeReservationButton);
        makeReservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToMakeReservation();
            }
        });
    }

    public void goToMakeReservation() {
        frame.dispose();
        MakeReservation makeReservationPage = new MakeReservation();
        makeReservationPage.setVisible(true);
    }

    public void viewHistoryButton() {
        JButton viewHistoryButton = new JButton("View History");
        viewHistoryButton.setBounds(145, 141, 310, 29); 
        frame.getContentPane().add(viewHistoryButton);
        viewHistoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToCustomerHistory();
            }
        });
    }

    public void goToCustomerHistory() {
        frame.dispose();
        CustomerHistory customerHistoryPage = new CustomerHistory();
        customerHistoryPage.setVisible(true);
    }

    public void purchaseGiftCardButton() {
        JButton purchaseGiftCardButton = new JButton("Purchase Gift Card");
        purchaseGiftCardButton.setBounds(145, 182, 310, 29); 
        frame.getContentPane().add(purchaseGiftCardButton);
        purchaseGiftCardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToPurchaseGiftCard();
            }
        });
    }

    public void goToPurchaseGiftCard() {
        frame.dispose();
        PurchaseGiftCard purchaseGiftCardPage = new PurchaseGiftCard();
        purchaseGiftCardPage.setVisible(true);
    }

    public void leaveAReviewButton() {
        JButton leaveAReviewButton = new JButton("Leave a Review");
        leaveAReviewButton.setBounds(145, 223, 310, 29);
        frame.getContentPane().add(leaveAReviewButton);
        leaveAReviewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goToLeaveReview();
            }
        });
    }

    public void goToLeaveReview() {
        frame.dispose();
        CustomerReview leaveReviewPage = new CustomerReview();
        leaveReviewPage.setVisible(true);
    }
    
    public void accountSettingsButton() {
        JButton accountSettingsButton = new JButton("Account Settings");
        accountSettingsButton.setBounds(145, 264, 310, 29);
        frame.getContentPane().add(accountSettingsButton);
        accountSettingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	goToAccountSettings();
            }
        });
    }

    public void goToAccountSettings() {
        frame.dispose();
        CustomerAccountSettings accountSettings = new CustomerAccountSettings();
        accountSettings.setVisible(true);
    }
    
    public void logoutButton() {
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(145, 308, 310, 29); // Adjust bounds as per your layout
        frame.getContentPane().add(logoutButton);
     
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current window
                SignInPage signInPage = new SignInPage(); // Create an instance of SignInPage
                signInPage.setVisible(true); // Show the SignInPage
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