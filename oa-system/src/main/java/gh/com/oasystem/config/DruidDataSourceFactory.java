package gh.com.oasystem.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

import javax.sql.DataSource;

public class DruidDataSourceFactory extends UnpooledDataSourceFactory {
    public DruidDataSourceFactory() {
        this.dataSource = new DruidDataSource();
    }
    @Override
    public DataSource getDataSource() {
        try{
            ((DruidDataSource)this.dataSource).init();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return this.dataSource;
    }
}
