/**
 * 
 */
package unExif;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * @author gatsby
 *
 */
public class Base extends JFrame implements ActionListener, ItemListener{
	private JLabel lblPathIn, lblPathOut;
	private JPanel pnInput, pnOutput, pnControls;
	private JTextField txtPathIn, txtPathOut;
	private JButton btnStart, btnBrowseIn, btnBrowseOut;
	private JCheckBox chkReplace;
	private JFileChooser fc;
	private File fileInput, fileOutput;
	private FileFilter filterImages;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Base();
	}
	public Base(){
		setTitle("unExif");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		createComponents();
	}

	private void createComponents() {
		// TODO Auto-generated method stub
		txtPathIn = new JTextField(20);
		txtPathOut = new JTextField(20);
		txtPathIn.setEditable(false);
		txtPathOut.setEditable(false);
		btnStart = new JButton("unExif photo");
		btnStart.setEnabled(false);
		chkReplace = new JCheckBox("Replace file");
		chkReplace.setSelected(true);
		chkReplace.addItemListener(this);
		btnStart.addActionListener(this);
		btnStart.setActionCommand("start");
		btnBrowseIn = new JButton("Browse..");
		btnBrowseOut = new JButton("Browse..");
		btnBrowseIn.addActionListener(this);	
		btnBrowseIn.setActionCommand("browsein");
		btnBrowseOut.addActionListener(this);
		btnBrowseOut.setActionCommand("browseout");
		lblPathIn = new JLabel("Path to photo: ");
		lblPathOut = new JLabel("Path to output:");		

		createLayout();
	}
	private void createLayout() {
		// TODO Auto-generated method stub
		pnInput = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnInput.setBorder(BorderFactory.createTitledBorder("Input"));
		pnInput.add(lblPathIn);
		pnInput.add(txtPathIn);
		pnInput.add(btnBrowseIn);

		pnOutput = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnOutput.setBorder(BorderFactory.createTitledBorder("Output"));
		pnOutput.add(lblPathOut);
		pnOutput.add(txtPathOut);
		pnOutput.add(btnBrowseOut);
		pnOutput.setVisible(false);

		pnControls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnControls.add(chkReplace);
		pnControls.add(btnStart);
		getContentPane().add(pnInput, BorderLayout.NORTH);
		getContentPane().add(pnOutput, BorderLayout.CENTER);
		getContentPane().add(pnControls, BorderLayout.SOUTH);		
		pack();			
		setLocationRelativeTo(null);
		setVisible(true);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnStart){
			System.out.println("Start called.");
			
			if(chkReplace.isSelected()){
				try {
					BufferedImage bfImage = ImageIO.read(fileInput);
					ImageIO.write(bfImage, getFileExtension(fileInput), fileInput);
					System.out.println("Successfully replaced metadata of "+ fileInput.getName());
					
					//ImageIO.write(bfImage, fileInput.get, output)
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				try{
					BufferedImage bfImage = ImageIO.read(fileInput);
					ImageIO.write(bfImage, getFileExtension(fileInput), fileOutput);
					
				}catch (IOException e1){
					e1.printStackTrace();
				}
			}
		
			

		}else if(e.getSource() == btnBrowseIn){
			fc = new JFileChooser();
			filterImages = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
			fc.addChoosableFileFilter(filterImages);
			fc.setAcceptAllFileFilterUsed(false);
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				fileInput = fc.getSelectedFile();
				txtPathIn.setText(fileInput.getAbsolutePath());
				if(chkReplace.isSelected()){
					btnStart.setEnabled(true);
				}
			}
			
		}else if(e.getSource() == btnBrowseOut){
			fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				fileOutput = fc.getSelectedFile();
				txtPathOut.setText(fileOutput.getAbsolutePath());
				btnStart.setEnabled(true);
			}
		}
	}
	 private static String getFileExtension(File file) {
	        String fileName = file.getName();
	        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
	        return fileName.substring(fileName.lastIndexOf(".")+1);
	        else return "";
	    }
	@Override	
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getItem() == chkReplace && e.getStateChange() == ItemEvent.SELECTED){
			pnOutput.setVisible(false);
			if(!txtPathIn.getText().isEmpty()){
				btnStart.setEnabled(true);
			}
			pack();
			setLocationRelativeTo(null);
		}else if(e.getItem() == chkReplace && e.getStateChange() == ItemEvent.DESELECTED){			
			pnOutput.setVisible(true);
			btnStart.setEnabled(false);
			pack();
			setLocationRelativeTo(null);		
		}

	}

}
