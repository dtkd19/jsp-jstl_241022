package handler;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileRemoveHandler {
	private static final Logger log = LoggerFactory.getLogger(FileRemoveHandler.class);
	
	
	// savePath, imageFileName 매개변수로 받아서 파일을 삭제하는 메서드 
	public int deleteFile(String savePath, String imageFileName) {
		// return 삭제 여부의 값을 리턴 
		// 파일 삭제시 ... 경로포함 파일명.delete return true/false 로 리턴 
		boolean isDel = false; // 삭제가 잘 되었는지 체크 변수
		log.info(">>> deleteFile method 접근 > {} ", imageFileName);
		
		// 기존 파일 이름 객체 생성
		File fileDir = new File(savePath);
		File removeFile = new File(fileDir+File.separator+imageFileName);
		File removeThFile = new File(fileDir+File.separator+"_th_"+imageFileName);
		
		
		// 파일이 존재해야 삭제
		if(removeFile.exists() || removeThFile.exists()) {
			isDel = removeFile.delete(); // 원래파일 삭제
			log.info(">>> removeFile !! > {} ", isDel);
			if(isDel) {
				isDel = removeThFile.delete();
				log.info(">>> removeThFile !! > {} ", isDel);
			}
		}
		
		return isDel? 1 : 0 ;
	}

}
