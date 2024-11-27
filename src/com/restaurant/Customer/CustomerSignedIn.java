package com.restaurant.Customer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

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
		frame = new JFrame("");
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLookAndFeel();
		
		changePasswordButton();
		changeNameButton();
		changeAddressButton();
		paymentInfoButton();
		orderOnlineButton();
		makeReservationButton();
		viewHistoryButton();
		purchaseGiftCardButton();
		leaveAReviewButton();		
	}
	
	public void changePasswordButton() {
		frame.getContentPane().setLayout(null);
		JButton changePasswordButton = new JButton("Change Password");
		changePasswordButton.setBounds(250, 49, 310, 29);
		frame.getContentPane().add(changePasswordButton);
		changePasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustomerChangePassword();
			}
		});
	}
	public void goToCustomerChangePassword() {
		frame.dispose();
		CustomerChangePassword CCP = new CustomerChangePassword(); 
		CCP.initialize();
		CCP.frame.setVisible(true);
	}
	
	
	
	public void changeNameButton() {
		JButton changeNameButton = new JButton("Change Name");
		changeNameButton.setBounds(250, 89, 310, 29);
		frame.getContentPane().add(changeNameButton);
		changeNameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustomerChangeName();
			}
		});
	}
	public void goToCustomerChangeName() {
		frame.dispose();
		CustomerChangeName CCN = new CustomerChangeName(); 
		CCN.initialize();
		CCN.frame.setVisible(true);
	}
	public void changeAddressButton() {
		JButton changeAddressButton = new JButton("Change Address");
		changeAddressButton.setBounds(250, 129, 310, 29);
		frame.getContentPane().add(changeAddressButton);
		changeAddressButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustomerChangeAddress();
			}
		});
	}
	public void goToCustomerPaymentInfo() {
		frame.dispose();
		CustomerPaymentInfo CPI = new CustomerPaymentInfo(); 
		CPI.initialize();
		CPI.frame.setVisible(true);
	}
	public void paymentInfoButton() {
		JButton paymentInfoButton = new JButton("Enter Payment Information");
		paymentInfoButton.setBounds(250, 209, 310, 29);
		frame.getContentPane().add(paymentInfoButton);
		paymentInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goToCustomerPaymentInfo();
			}
		});
	}
	public void goToCustomerChangeAddress() {
		frame.dispose();
		CustomerChangeAddress CCA = new CustomerChangeAddress(); 
		CCA.initialize();
		CCA.frame.setVisible(true);
	}
	
	
	
	public void orderOnlineButton() {
        JButton orderOnlineButton = new JButton("Order Online");
        orderOnlineButton.setBounds(250, 249, 310, 29); 
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
        makeReservationButton.setBounds(250, 289, 310, 29); 
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
        viewHistoryButton.setBounds(250, 329, 310, 29); 
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
        purchaseGiftCardButton.setBounds(250, 369, 310, 29); 
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
        leaveAReviewButton.setBounds(250, 409, 310, 29);
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
	
	
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) { }
	}
	
	public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

}
