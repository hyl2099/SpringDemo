package com.example.onlineMenu.repository.menu;

import com.example.onlineMenu.documents.menu.MenuDishList;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MenuDishListRepository  extends CrudRepository<MenuDishList, Long> {
    @Transactional//开启事务
    @Query("update MenuDishList mdl set " +
            "mdl.menuId = ?1, mdl.name = ?2, mdl.pictureId = ?3, mdl.description = ?4, mdl.price = ?5, mdl.isInMenuOrAd = ?6, mdl.discountPrice = ?7 where mdl.id = ?8")
    void updateMenuDishList(Long menuId, String name, Long pictureId, String description, Float price, int isInMenuOrAd, Float discountPrice, Long id);

    //另一种写法
    @Transactional//开启事务
    @Query("update MenuDishList mdl set mdl.isInMenuOrAd =:isInMenuOrAd, mdl.discountPrice =:discountPrice where mdl.id =:id")
    void markEntryAsRead(@Param("isInMenuOrAd") int isInMenuOrAd, @Param("discountPrice") Float discountPrice, @Param("id")Long id);


    //   JPA的查询语言,类似于sql
    //   里面不能出现表名,列名,只能出现java的类名,属性名，区分大小写
    //   出现的sql关键字是一样的意思,不区分大小写
    //   不能写select *  要写select 别名
    @Transactional//开启事务
    @Query("select mdl from MenuDishList mdl where mdl.isInMenuOrAd = 1")
    List<MenuDishList> findOnMenuDishList();
    //isInMenuOrAd 0为draft,1为在菜单上,2为既在菜单上又在广告上。

    @Transactional//开启事务
    @Query("select mdl from MenuDishList mdl where mdl.isInMenuOrAd = 2")
    List<MenuDishList> findOnADDishList();
}
