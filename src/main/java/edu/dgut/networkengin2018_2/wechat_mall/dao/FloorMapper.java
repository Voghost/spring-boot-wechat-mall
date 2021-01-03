package edu.dgut.networkengin2018_2.wechat_mall.dao;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.dgut.networkengin2018_2.wechat_mall.entity.Floor;
import edu.dgut.networkengin2018_2.wechat_mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FloorMapper {
   /**
    * 新增一行楼层
    * @param floor
    * @return
    */
   int insertFloor(Floor floor);

   /**
    * 删除一行楼层
    */
   int deleteFloor(Integer floorId);

   /**
    * 更新一行楼层
    */
   int updateFloor(Floor floor);

   /**
    * 分页查询(包括关键字)
    * @return
    */
   List<Floor> findFloorList(PageQueryUtil pageQueryUtil);

   int getTotalFloors();

   int deleteBatch(Integer[] ids);

   Floor selectByPrimaryKey(Integer floorId);

   List<Floor> getAllFloorList();

}
