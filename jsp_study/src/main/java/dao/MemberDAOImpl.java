package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.MemberVO;
import orm.DatabaseBuilder;

public class MemberDAOImpl implements MemberDAO {

	private static final Logger log = LoggerFactory.getLogger(MemberDAOImpl.class);
	private SqlSession sql;
	
	public MemberDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}

	@Override
	public int register(MemberVO mvo) {
		
		log.info(">>>> register DAO in !!");
		int isOk = sql.insert("MemberMapper.register", mvo);
		if(isOk > 0) {
			sql.commit();
		}	
		return isOk;
		
	}

	@Override
	public MemberVO login(MemberVO mvo) {
		log.info(">>>> login DAO in !!");
		return sql.selectOne("MemberMapper.login",mvo);
	}

	@Override
	public int lastLogin(String id) {
		log.info(">>>> lastLogin DAO in !!");
		int isOk = sql.update("MemberMapper.last", id);
		if(isOk > 0) {
			sql.commit();
		}	
		return isOk;
	}

	@Override
	public List<MemberVO> getList() {
		log.info(">>> userList DAO in !! ");
		return sql.selectList("MemberMapper.user");
	}

	@Override
	public int modify(MemberVO mvo) {
		log.info(">>>> userInfo DAO in !!");
		int isOk = sql.update("MemberMapper.info", mvo);
		if(isOk > 0) {
			sql.commit();
		}	
		return isOk;
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		log.info(">>>> userDelete DAO in !!");
		int isOk = sql.delete("MemberMapper.delete", id);
		if(isOk > 0) {
			sql.commit();
		}	
		return isOk;
	}

	@Override
	public List<BoardVO> getBoard(String id) {
		log.info(">>>> userBoard DAO in !!");
		return sql.selectList("MemberMapper.board", id);
	}

	@Override
	public MemberVO getDetail(String id) {
		log.info(">>>> getDetail DAO in !!");
		return sql.selectOne("MemberMapper.detail",id);
	}

	
	
	
}
