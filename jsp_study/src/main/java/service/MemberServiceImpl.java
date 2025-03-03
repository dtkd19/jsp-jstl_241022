package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.MemberDAO;
import dao.MemberDAOImpl;
import domain.BoardVO;
import domain.MemberVO;

public class MemberServiceImpl implements MemberService {

	private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	private MemberDAO mdao;
	
	public MemberServiceImpl() {
		mdao = new MemberDAOImpl();
	}

	@Override
	public int register(MemberVO mvo) {
		// TODO Auto-generated method stub
		return mdao.register(mvo);
	}

	@Override
	public MemberVO login(MemberVO mvo) {
		// TODO Auto-generated method stub
		return mdao.login(mvo);
	}

	@Override
	public int lastLogin(String id) {
		// TODO Auto-generated method stub
		return mdao.lastLogin(id);
	}

	@Override
	public List<MemberVO> getList() {
		// TODO Auto-generated method stub
		return mdao.getList();
	}

	@Override
	public int modify(MemberVO mvo) {
		// TODO Auto-generated method stub
		return mdao.modify(mvo);
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		return mdao.delete(id);
	}

	@Override
	public List<BoardVO> getBoard(String id) {
		// TODO Auto-generated method stub
		return mdao.getBoard(id);
	}

	@Override
	public MemberVO getDetail(String id) {
		// TODO Auto-generated method stub
		return mdao.getDetail(id);
	}

	
}
