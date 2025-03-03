package service;

import java.util.List;

import domain.BoardVO;
import domain.MemberVO;

public interface MemberService {

	int register(MemberVO mvo);

	MemberVO login(MemberVO mvo);

	int lastLogin(String id);

	List<MemberVO> getList();

	int modify(MemberVO mvo);

	int delete(String id);

	List<BoardVO> getBoard(String id);

	MemberVO getDetail(String id);


}
