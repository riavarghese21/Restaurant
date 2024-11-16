package com.restaurant;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class Cart {
	
	private static Cart instance;

	private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JButton RemoveFromCartButton;
    private JButton ModifyItemButton;
    private JButton AddMoreItemsButton;
    private JButton GoToPaymentButton;
    private JLabel ViewCartLabel;
    private JButton BackButton;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cart window = new Cart();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static Cart getInstance() {
	    if (instance == null) {
	        instance = new Cart();
	    }
	    return instance;
	}


	/**
	 * Create the application.
	 */
	public Cart() {
		initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Cart Table
        String[] columnNames = {"Name", "Price", "Quantity", "Total"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        JScrollPane Cart = new JScrollPane(table);
        Cart.setBounds(55, 63, 400, 400);
        frame.getContentPane().add(Cart);

        JLabel CartLabel = new JLabel("Cart");
        CartLabel.setBounds(235, 35, 34, 16);
        frame.getContentPane().add(CartLabel);
        
        RemoveFromCartButton = new JButton("Remove From Cart");
        RemoveFromCartButton.setBounds(527, 193, 153, 29);
        frame.getContentPane().add(RemoveFromCartButton);
        
        ModifyItemButton = new JButton("Modify Item");
        ModifyItemButton.setBounds(527, 261, 153, 29);
        frame.getContentPane().add(ModifyItemButton);
        
        AddMoreItemsButton = new JButton("Add More Items");
        AddMoreItemsButton.setBounds(527, 316, 153, 29);
        frame.getContentPane().add(AddMoreItemsButton);
        
        GoToPaymentButton = new JButton("Go to Payment");
        GoToPaymentButton.setBounds(543, 503, 117, 29);
        frame.getContentPane().add(GoToPaymentButton);
        
        ViewCartLabel = new JLabel("View Cart");
        ViewCartLabel.setBounds(419, 6, 61, 16);
        frame.getContentPane().add(ViewCartLabel);
        
        BackButton = new JButton("Back");
        BackButton.setBounds(205, 503, 117, 29);
        frame.getContentPane().add(BackButton);
		
	}
	
	public void addToCart(String name, double price, int quantity, double total) {
	    model.addRow(new Object[]{name, "$" + String.format("%.2f", price), quantity, "$" + String.format("%.2f", total)});
	}


    // Method to set Cart frame visibility
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

}


