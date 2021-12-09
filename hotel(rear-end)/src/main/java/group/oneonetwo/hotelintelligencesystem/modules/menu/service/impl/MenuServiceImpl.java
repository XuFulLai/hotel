package group.oneonetwo.hotelintelligencesystem.modules.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.menu.dao.MenuMapper;
import group.oneonetwo.hotelintelligencesystem.modules.menu.model.po.MenuPO;
import group.oneonetwo.hotelintelligencesystem.modules.menu.model.vo.MenuVO;
import group.oneonetwo.hotelintelligencesystem.modules.menu.service.IMenuService;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.model.vo.MenuDeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.menu_dept.service.IMenuDeptService;
import group.oneonetwo.hotelintelligencesystem.tools.ConvertUtils;
import group.oneonetwo.hotelintelligencesystem.tools.WStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 文
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class MenuServiceImpl implements IMenuService {

    @Autowired
    IMenuDeptService menuDeptService;

    @Autowired
    MenuMapper menuMapper;

    @Override
    public MenuVO add(MenuVO vo) {
        if (WStringUtils.isBlank(vo.getpId())) {
            vo.setpId("0");
        }
        // 处理节点层级
        if ( "0".equals(vo.getpId()) || vo.getpId() == null) {
            vo.setLevel(1);
        }else {
            MenuPO menuPO = menuMapper.selectById(vo.getpId());
            if (menuPO == null) {
                throw new CommonException("未找到此父菜单");
            }
            vo.setLevel(menuPO.getLevel() + 1);
        }
        MenuPO menuPO = new MenuPO();
        BeanUtils.copyProperties(vo,menuPO);
        int insert = menuMapper.insert(menuPO);
        int addNum = 0;

        if (!WStringUtils.isBlank(vo.getRole()) || !WStringUtils.isBlank(vo.getDeptId())) {
            // 插入权限表
            if (insert != 0) {
                addNum = menuDeptService.batchAdd(vo.getId(), vo);
            }
            if (addNum == 0){
                throw new CommonException("插入异常");
            }
        }
        return vo;
    }

    @Override
    public  MenuPO save(MenuVO menuVO){
        if(menuVO==null){
            throw new CommonException(501,"menu实体为空");
        }
        MenuVO check=selectByIdReturnVO(menuVO.getId());
        if (check==null){
            throw new CommonException(4004,"找不到id为'" + menuVO.getId() + "'的数据");
        }
        MenuPO menuPO=new MenuPO();
        BeanUtils.copyProperties(menuVO,menuPO);
        int save=menuMapper.updateById(menuPO);
        if (!WStringUtils.isBlank(menuVO.getRole()) || !WStringUtils.isBlank(menuVO.getDeptId())) {
            // 插入权限表

            int addNum = menuDeptService.batchAdd(menuVO.getId(), menuVO);

            if (addNum == 0){
                throw new CommonException("插入异常");
            }
        }
        if(save>0){
            return menuMapper.selectById(menuPO.getId());
        }
        throw new SavaException("更改菜单失败");
    }


    @Override
    public Integer delete(String id) {
        selectByIdReturnVO(id);
        int i = menuMapper.deleteById(id);
        return i;
    }

    @Override
    public List<MenuVO> getMenuTreeByDeptIdAndRole(MenuDeptVO vo) {
        // 获取该id有权限访问的所有菜单
        List<MenuVO> allMenu = menuMapper.getMenuTreeByDeptIdAndRole(vo);
        return list2Tree(allMenu);
    }

    @Override
    public MenuVO selectByIdReturnVO(String id) {
        MenuPO menuPO = menuMapper.selectById(id);
        if (menuPO == null) {
            throw new CommonException("该数据不存在");
        }
        MenuVO menuVO = new MenuVO();
        BeanUtils.copyProperties(menuPO,menuVO);
        return menuVO;
    }

    @Override
    public List<MenuVO> getMenuTree(MenuVO menuVO) {
        // 构建查询条件
        QueryWrapper<MenuPO> wrapper = new QueryWrapper<>();
        if (!"".equals(menuVO.getName()) && menuVO.getName() != null) {
            wrapper.like("name",menuVO.getName());
        }
        if (!"".equals(menuVO.getVisible()) && menuVO.getVisible() != null) {
            wrapper.eq("visible",menuVO.getVisible());
        }
        wrapper.orderByAsc("p_id").orderByAsc("sort");
        List<MenuPO> menuPOS = menuMapper.selectList(wrapper);
        return list2Tree(ConvertUtils.transferList(menuPOS,MenuVO.class));
    }

    private List<MenuVO> list2Tree(List<MenuVO> allMenu) {
        List<MenuVO> resMenu = new ArrayList<>();
        for (MenuVO item : allMenu) {
            if (!"0".equals(item.getpId())) {
                break;
            }
            MenuVO menuVO = addChild(item, allMenu);
            resMenu.add(menuVO);
        }
        return resMenu;
    }

    private MenuVO addChild(MenuVO current,List<MenuVO> allMenu){
        List<MenuVO> children = new ArrayList<>();
        for (MenuVO item : allMenu) {
            if (item.getpId().equals(current.getId())) {
                item = addChild(item,allMenu);
                children.add(item);
            }
        }
        current.setChildren(children);
        return current;
    }

    @Override
    public MenuVO saveone(MenuVO menuVO){
        MenuPO save=save(menuVO);
        BeanUtils.copyProperties(save,menuVO);
        return menuVO;
    }


    @Override
    public Page<MenuVO> getPage(MenuVO menuVO){
        QueryWrapper<MenuPO> wrapper=new QueryWrapper<>();
        Page<MenuPO> page=new Page<>(menuVO.getPage().getPage(),menuVO.getPage().getSize());
        Page<MenuPO> poiPage=(Page<MenuPO>) menuMapper.selectPage(page,wrapper);
        return ConvertUtils.transferPage(poiPage,MenuVO.class);

    }
}
