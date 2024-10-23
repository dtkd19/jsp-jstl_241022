package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CommentVO;
import orm.DatabaseBuilder;


public class CommentDAOImpl implements CommentDAO {
	private static final Logger log = LoggerFactory.getLogger(CommentDAOImpl.class);
	
	private SqlSession sql;
	
	public CommentDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}

	@Override
	public int post(CommentVO cvo) {
		log.info("comment dao post in");
		int isOk = sql.insert("CommentMapper.post",cvo);
		
		if(isOk > 0 ) {
			sql.commit();
		}
		
		return isOk;
	}

	@Override
	public List<CommentVO> getList(int bno) {
		log.info("comment dao list in");
		
		return sql.selectList("CommentMapper.list", bno);
	}
	
	
	
	
	
}
