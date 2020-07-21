package imgResizeProj;



import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;

public class ImgResize {





	public void getImgResize(List<String> filePathList, String resizeFilePath, double scale, String resizeWidth, JTextArea area1, int method_flag) {
		/*
		//作業対象ファイルを格納
		List<String> filePathList = new ArrayList<String>();
		//リサイズ後のファイル格納フォルダのパス
		String resizeFilePath = "C:\\";
		//倍率リサイズの値
		double scale = 0.5;
		//幅基準リサイズの値
		String resizeWidth = "300";
		*/

		int m_flag = method_flag;
		List<String> fileList = filePathList;
		double resizeSize = scale;
		String rfPath = resizeFilePath;
		int rWidth = 0;
		if(m_flag == 2){
			rWidth = 0;
		}else if(m_flag == 1){
			rWidth = Integer.parseInt(resizeWidth);
		}



//		System.out.println("filePathList:" +filePathList.toString());
//		System.out.println("resizeFilePath:" + resizeFilePath);
//		System.out.println("scale:" + scale);
//		System.out.println("resizeWidth:" + resizeWidth);
//		System.out.println("method_flag:" + method_flag);
//		System.out.println("fileListサイズ:" + fileList.size());


		BufferedImage readImage = null;
		BufferedImage resizedImage = null;

        area1.append("\n");
        area1.append("リサイズを実行します！");

		//リサイズの処理
		for(String fplist : fileList){
			//バイナリデータに変換

			try {
				readImage = ImageIO.read(new File(fplist));

				//ファイルまでのパス
				int point1 = fplist.lastIndexOf("\\");
				String pathName = fplist.substring(0, point1 + 1);

				//ファイル名
				int point2 = fplist.lastIndexOf(".");
				String fileName = fplist.substring(0, point2);
				fileName = fileName.replace(pathName, "");

				//拡張子
				int pathNameLen = pathName.length();
				int fileNameLen = fileName.length();
				int point3 = pathNameLen + fileNameLen;
				int extensionLen = fplist.length() - point3;
				String extensionName = fplist.substring(point3 + 1, point3 + extensionLen);


				//リサイズ実行
				scaleImage(readImage, resizeSize, rWidth, pathName, fileName, extensionName, rfPath, m_flag, area1);


			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("ファイル出力失敗");
				readImage = null;
			}
		}
        area1.append("\n");
        area1.append("全" + fileList.size() + "ファイルのリサイズ処理が完了しました！");
	}

//リサイズ処理の実行メソッド
	private void scaleImage(BufferedImage readImage, double scale, int rWidth, String pathName, String fileName, String extensionName, String rfPath, int m_flag, JTextArea area1) throws IOException {
        BufferedImage org = readImage;
        ImageFilter filter = null;

        //System.out.println("getType:" + org.getType());

        if(m_flag == 1){
            filter = new AreaAveragingScaleFilter(
               	 (int)(rWidth), (int)(rWidth * org.getHeight() / org.getWidth()));
        }else if(m_flag == 2){
            filter = new AreaAveragingScaleFilter(
               	 (int)(org.getWidth() * scale), (int)(org.getHeight() * scale));
        }

        ImageProducer p = new FilteredImageSource(org.getSource(), filter);
        java.awt.Image dstImage = Toolkit.getDefaultToolkit().createImage(p);

        BufferedImage dst = null;
        //BufferedImageのタイプ別に処理
        if(org.getType() == 5){
        	//jpgの場合
            dst = new BufferedImage(
                    dstImage.getWidth(null), dstImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
        }else if(org.getType() == 6){
        	//pngの場合
            dst = new BufferedImage(
                    dstImage.getWidth(null), dstImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        }

        Graphics2D g = dst.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
        g.drawImage(dstImage, 0, 0, null);
        g.dispose();

        if(rfPath == null){
        ImageIO.write(dst, extensionName, new File(pathName + fileName + "_resize." + extensionName));
        area1.append("\n");
        area1.append(pathName + fileName + "_resize." + extensionName + "作成完了！");
        }else{
            ImageIO.write(dst, extensionName, new File(rfPath + "\\" + fileName + "." + extensionName));
            area1.append("\n");
            area1.append(rfPath + "\\" + fileName + "." + extensionName + "作成完了！");
        }
	}
}
