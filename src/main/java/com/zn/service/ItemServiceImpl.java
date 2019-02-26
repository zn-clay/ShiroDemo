package com.zn.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zn.mapper.ItemsMapper;
import com.zn.pojo.Items;
import com.zn.pojo.ItemsExample;
@Service
public class ItemServiceImpl implements ItemService{
	@Resource
	private ItemsMapper itemsMapper;
	public int countByExample(ItemsExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteByExample(ItemsExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insert(Items record) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int insertSelective(Items record) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Items> selectByExample(ItemsExample example) {
		List<Items> list = itemsMapper.selectByExample(null);
		return list;
	}

	public Items selectByPrimaryKey(Integer id) {
		return itemsMapper.selectByPrimaryKey(id);
	}

	public int updateByExampleSelective(Items record, ItemsExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int updateByExample(Items record, ItemsExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int updateByPrimaryKeySelective(Items record) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int updateByPrimaryKey(Items record) {
		return itemsMapper.updateByPrimaryKey(record);
	}

}
