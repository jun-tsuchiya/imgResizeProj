package imgResizeProj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;

public class ImgResizeApp extends JFrame implements ActionListener {

	JLabel folderPathLabel1;
	JTextField folderPath1;
	JButton folderPathBtn1;
	JLabel folderPathLabel2;
	JCheckBox fp2checkbox;
	JTextField folderPath2;// 指定がない場合ファイル名の拡張子前に_resizeをつけてfolderPath1に配置
	JButton folderPathBtn2;
	JRadioButton wcheckbox;
	JTextField wResize;// Max 1200px、半角の0-9の数字のみ
	JLabel wResizeUnit;// pixel
	JRadioButton pcheckbox;;// Max 200%
	JComboBox<String> pResize;// 10%-200%
	DefaultComboBoxModel<String> model;
	JButton button;
	JTextArea area1;
	ButtonGroup group;

	// 作業対象ファイルを格納
	List<String> filePathList = new ArrayList<String>();
	// フォルダ指定の有無のフラグ
	int fpchkFlag = 0;
	// リサイズ後のファイル格納フォルダのパス
	String resizeFilePath = null;
	// 倍率リサイズの値
	double scale = 999.99;
	// 幅基準リサイズの値
	String resizeWidth = "0";
	//リサイズ方法の選択フラグ1=幅基準、2=倍率基準
	int method_flag = 1;

	public static void main(String[] args) {
		ImgResizeApp frame = new ImgResizeApp();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 場所
		frame.setBounds(0, 0, 800, 500);
		frame.setLocationRelativeTo(null);

		// タイトル
		frame.setTitle("画像ファイルをフォルダ単位でリサイズします");

		// アイコン
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage("C:\\xampp\\img\\bitnami.ico");
		frame.setIconImage(img);
		frame.setVisible(true);
	}

	ImgResizeApp() {
		SpringLayout layout = new SpringLayout();
		JPanel p = new JPanel();
		p.setLayout(layout);

		// パーツ生成
		folderPathLabel1 = new JLabel("リサイズしたい画像ファイルが格納されているフォルダを指定してください");
		folderPath1 = new JTextField();
		folderPath1.setBackground(Color.white);
		folderPath1.setEditable(false);
		folderPath1.setCaretColor(Color.WHITE);
		folderPathBtn1 = new JButton("参照");
		folderPathBtn1.setPreferredSize(new Dimension(200, 20));
		folderPathBtn1.addActionListener(this);
		folderPathBtn1.setActionCommand("fPBtn1_push");
		fp2checkbox = new JCheckBox("リサイズしたファイルを指定したフォルダに格納する", false);
		fp2checkbox.setActionCommand("fp2On");
		fp2checkbox.addActionListener(this);
		folderPath2 = new JTextField();
		folderPath2.setBackground(Color.LIGHT_GRAY);
		folderPath2.setEditable(false);
		folderPath2.setCaretColor(Color.WHITE);
		folderPathBtn2 = new JButton("参照");
		folderPathBtn2.setEnabled(false);
		folderPathBtn2.setPreferredSize(new Dimension(200, 20));
		folderPathBtn2.addActionListener(this);
		folderPathBtn2.setActionCommand("fPBtn2_push");
		wcheckbox = new JRadioButton("横幅を基準にしてリサイズする", true);
		wcheckbox.addActionListener(this);
		wcheckbox.setActionCommand("wChked");
		wResize = new JTextField("----");
		wResizeUnit = new JLabel("pixel");
		pcheckbox = new JRadioButton("倍率を指定してリサイズする", false);
		pcheckbox.addActionListener(this);
		pcheckbox.setActionCommand("pChked");
		String[] combodata = { "----", "10%", "30%", "50%", "70%", "90%", "120%", "150%", "180%", "200%" };
		model = new DefaultComboBoxModel(combodata);
		pResize = new JComboBox<String>(model);
		pResize.setActionCommand("pResizeSelected");
		pResize.addActionListener(this);
		pResize.setPreferredSize(new Dimension(180, 25));
		pResize.setEnabled(false);
		button = new JButton("実行");
		button.addActionListener(this);
		button.setActionCommand("button_push");
		area1 = new JTextArea();
		EtchedBorder border = new EtchedBorder(EtchedBorder.LOWERED);
		area1.setBorder(border);
		JScrollPane scrollpane = new JScrollPane(area1);
		area1.setEditable(false);
		area1.setDisabledTextColor(Color.BLACK);

	    ButtonGroup group = new ButtonGroup();
	    group.add(wcheckbox);
	    group.add(pcheckbox);

		// パーツレイアウト
		layout.putConstraint(SpringLayout.NORTH, folderPathLabel1, 30, SpringLayout.NORTH, p);
		layout.putConstraint(SpringLayout.WEST, folderPathLabel1, 50, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.EAST, folderPathLabel1, -160, SpringLayout.EAST, p);
		layout.putConstraint(SpringLayout.NORTH, folderPath1, 10, SpringLayout.SOUTH, folderPathLabel1);
		layout.putConstraint(SpringLayout.WEST, folderPath1, 50, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.EAST, folderPath1, -160, SpringLayout.EAST, p);
		layout.putConstraint(SpringLayout.NORTH, folderPathBtn1, 10, SpringLayout.SOUTH, folderPathLabel1);
		layout.putConstraint(SpringLayout.WEST, folderPathBtn1, 10, SpringLayout.EAST, folderPath1);
		layout.putConstraint(SpringLayout.EAST, folderPathBtn1, 100, SpringLayout.EAST, folderPath1);

		layout.putConstraint(SpringLayout.NORTH, fp2checkbox, 20, SpringLayout.SOUTH, folderPathBtn1);
		layout.putConstraint(SpringLayout.WEST, fp2checkbox, 46, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.EAST, fp2checkbox, -160, SpringLayout.EAST, p);
		layout.putConstraint(SpringLayout.NORTH, folderPath2, 10, SpringLayout.SOUTH, fp2checkbox);
		layout.putConstraint(SpringLayout.WEST, folderPath2, 50, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.EAST, folderPath2, -160, SpringLayout.EAST, p);
		layout.putConstraint(SpringLayout.NORTH, folderPathBtn2, 10, SpringLayout.SOUTH, fp2checkbox);
		layout.putConstraint(SpringLayout.WEST, folderPathBtn2, 10, SpringLayout.EAST, folderPath2);
		layout.putConstraint(SpringLayout.EAST, folderPathBtn2, 100, SpringLayout.EAST, folderPath2);

		layout.putConstraint(SpringLayout.NORTH, wcheckbox, 20, SpringLayout.SOUTH, folderPathBtn2);
		layout.putConstraint(SpringLayout.WEST, wcheckbox, 46, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.EAST, wcheckbox, 250, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.NORTH, wResize, 10, SpringLayout.SOUTH, wcheckbox);
		layout.putConstraint(SpringLayout.WEST, wResize, 50, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.EAST, wResize, 100, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.NORTH, wResizeUnit, 10, SpringLayout.SOUTH, wcheckbox);
		layout.putConstraint(SpringLayout.WEST, wResizeUnit, 10, SpringLayout.EAST, wResize);
		layout.putConstraint(SpringLayout.EAST, wResizeUnit, 100, SpringLayout.EAST, wResize);

		layout.putConstraint(SpringLayout.NORTH, pcheckbox, 10, SpringLayout.SOUTH, wResize);
		layout.putConstraint(SpringLayout.WEST, pcheckbox, 46, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.EAST, pcheckbox, 250, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.NORTH, pResize, 10, SpringLayout.SOUTH, pcheckbox);
		layout.putConstraint(SpringLayout.WEST, pResize, 50, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.EAST, pResize, 150, SpringLayout.WEST, p);

		layout.putConstraint(SpringLayout.NORTH, button, 20, SpringLayout.SOUTH, pResize);
		layout.putConstraint(SpringLayout.WEST, button, 50, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.EAST, button, 250, SpringLayout.WEST, p);
		layout.putConstraint(SpringLayout.SOUTH, button, 60, SpringLayout.NORTH, button);

		layout.putConstraint(SpringLayout.NORTH, scrollpane, 30, SpringLayout.SOUTH, folderPath2);
		layout.putConstraint(SpringLayout.WEST, scrollpane, 50, SpringLayout.EAST, wcheckbox);
		layout.putConstraint(SpringLayout.EAST, scrollpane, -60, SpringLayout.EAST, p);
		layout.putConstraint(SpringLayout.SOUTH, scrollpane, 400, SpringLayout.NORTH, area1);

		// コンポーネントに格納
		p.add(folderPathLabel1);
		p.add(folderPath1);
		p.add(folderPathBtn1);
		p.add(fp2checkbox);
		p.add(folderPath2);
		p.add(folderPathBtn2);
		p.add(wcheckbox);
		p.add(wResize);
		p.add(wResizeUnit);
		p.add(pcheckbox);
		p.add(pResize);
		p.add(button);
		p.add(scrollpane);

		getContentPane().add(p, BorderLayout.CENTER);

	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		// リサイズ対象のファイル格納場所
		if (cmd.equals("fPBtn1_push")) {
			// 再選択時にリセット
			filePathList.clear();
			area1.setText("選択したファイルは以下となります");
			// 作業対象フォルダの選択
			JFileChooser filechooser1 = new JFileChooser();
			// filechooser1.addChoosableFileFilter(new imageFilter());
			filechooser1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int selected1 = filechooser1.showSaveDialog(this);
			if (selected1 == filechooser1.APPROVE_OPTION) {
				File file1 = filechooser1.getSelectedFile();

				folderPath1.setText(file1.getAbsolutePath());
				if (file1.isDirectory()) {
					// List<String> filePathList = new ArrayList<String>();
					File[] files = null;
					// 指定したフォルダで該当するファイル名を取込む
					try {
						// ファイル一覧を取得（拡張子でフィルタリング）
						files = new File(folderPath1.getText()).listFiles(new addImageFilter());
						// files = new File(folderPath1.getText()).listFiles();
						if (!(files.length == 0)) {
							//System.out.println(files.toString());
							for (int i = 0; i < files.length; i++) {
								File file = files[i];
								if (file.isFile()) {
									String file_name = file.toString();
									filePathList.add(file_name);// ★★★★★
									area1.append("\n");
									area1.append(file_name);
								}
							}
							//System.out.println(filePathList.toString());
							//System.out.println(filePathList.size());
						} else {
							// ファイルが存在するか
							JFrame frame = new JFrame();
							JOptionPane.showMessageDialog(frame, "対象のファイルが見つかりません", "参照先を確認してください",
									JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception e1) {
						System.out.println("ファイルがない");
						e1.printStackTrace();
						files = null;
					}
				} else {
					// ファイルが存在するか
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "ファイルを指定することはできません。", "参照先を確認してください",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		// リサイズファイルの格納フォルダ指定を有効・無効にする
		if (cmd.equals("fp2On")) {
			if (fpchkFlag == 0) {
				folderPathBtn2.setEnabled(true);
				folderPath2.setBackground(Color.WHITE);
				fpchkFlag = 1;
			} else {
				folderPathBtn2.setEnabled(false);
				folderPath2.setBackground(Color.lightGray);
				folderPath2.setText("");
				resizeFilePath = null;
				fpchkFlag = 0;
			}
		}

		// リサイズファイルの格納フォルダを指定
		if (cmd.equals("fPBtn2_push")) {
			// リサイズファイル格納フォルダの選択
			JFileChooser filechooser2 = new JFileChooser();
			filechooser2.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int selected2 = filechooser2.showSaveDialog(this);
			if (selected2 == filechooser2.APPROVE_OPTION) {
				File file2 = filechooser2.getSelectedFile();
				if (file2.isDirectory()) {
					folderPath2.setText(file2.getAbsolutePath());
					resizeFilePath = file2.getAbsolutePath();
				} else {
					// ファイルが存在するか
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "ファイルを指定することはできません。", "参照先を確認してください",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		// 幅基準有効
		if (cmd.equals("wChked")) {
			// チェックボックスによる切り替え
			wResize.setEnabled(true);
			wResize.setBackground(Color.WHITE);
			pcheckbox.setSelected(false);
			pResize.setEnabled(false);
			//scale = 0;
			method_flag = 1;

		}

		// 倍率指定有効
		if (cmd.equals("pChked")) {
			// チェックボックスによる切り替え
			pResize.setEnabled(true);
			wResize.setEnabled(false);
			wResize.setBackground(Color.lightGray);
			wcheckbox.setSelected(false);
			//wResize.setText("0");
			method_flag = 2;

		}
		// 倍率指定の変換
		if (cmd.equals("pResizeSelected")) {
			String data = (String) model.getSelectedItem();
			// area1.append("\n");
			// area1.append("data:" + data);
			switch (data) {
			case "----":
				scale = 999.99;
				break;
			case "10%":
				scale = 0.1;
				break;
			case "30%":
				scale = 0.3;
				break;
			case "50%":
				scale = 0.5;
				break;
			case "70%":
				scale = 0.7;
				break;
			case "90%":
				scale = 0.9;
				break;
			case "120%":
				scale = 1.2;
				break;
			case "150%":
				scale = 1.5;
				break;
			case "180%":
				scale = 1.8;
				break;
			case "200%":
				scale = 2.0;
				break;
			default:
				scale = 999.99;
				break;
			}

		}
		// 実行ボタン
		if (cmd.equals("button_push")) {

			// 幅基準の値取得
			resizeWidth = wResize.getText();
			//area1.append(wResize.getText());
//			area1.append("\n");
//			area1.append("resizeWidth:" + resizeWidth);
//
//			area1.append("\n");
//			area1.append("scale:" + scale);

			int exe_check = 0;

//			System.out.println("method_flag:" + method_flag);
//			area1.append("\n");
//			area1.append("method_flag:" + method_flag);

			//作業対象フォルダの入力チェック
			if (filePathList.size() == 0) {
				JFrame frame = new JFrame();
				JOptionPane.showMessageDialog(frame, "ファイルを指定していません。", "参照先を確認してください", JOptionPane.ERROR_MESSAGE);
				exe_check++;
			}
			//幅基準入力チェック
			if(method_flag == 1){
				if (!resizeWidth.matches("[0-9]{1,3}")) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "幅基準は半角数字3桁で入力してください。", "入力範囲エラー", JOptionPane.ERROR_MESSAGE);
					exe_check++;
				}
			}
			//倍率基準入力チェック
			if(method_flag == 2){
				if (scale == 999.99) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "倍率を選択してください。", "倍率未選択エラー", JOptionPane.ERROR_MESSAGE);
					exe_check++;
				}
			}

//			area1.append("\n");
//			area1.append("exe_check:" + exe_check);
			if (exe_check == 0) {
				ImgResize imgResize = new ImgResize();
				imgResize.getImgResize(filePathList, resizeFilePath, scale, resizeWidth, area1, method_flag);

			}

		}

	}

}