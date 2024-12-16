package gh.com.oasystem.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;

public class MybatisUtil {
    private static SqlSessionFactory sqlSessionFactory;
    static {
        Reader reader;
        try {
            reader=  Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory =new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /*
     *
     *
     * @param function
     * @return {@link Object}
     */
    public static Object executeQuery(Function<SqlSession,Object> function){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{

            return function.apply(sqlSession);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            sqlSession.close();
        }
    }
    public static Object executeUpdate(Function<SqlSession,Object> function){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Object result;
        try {

            result = function.apply(sqlSession);
            sqlSession.commit();
            return result;
        }catch (Exception e){
            sqlSession.rollback();
            throw e;
        }finally {
            sqlSession.close();
        }
    }
}
