package com.zn.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zn.pojo.Items;
import com.zn.service.ItemService;

@Controller
@RequestMapping("/items")
public class ItemsController {
	@Resource
	private ItemService itemService;
	@RequestMapping("/queryItems")
	@RequiresPermissions("item:query")
	public String queryItems(Model model) {
		List<Items> list = itemService.selectByExample(null);
		model.addAttribute("itemsList", list);
		return "itemsList";
	}
	@RequestMapping("/editItems")
	@RequiresPermissions("item:update")
	public String editItems(int id, Model model) {
		Items items = itemService.selectByPrimaryKey(id);
		model.addAttribute("items", items);
		return "editItem";
	}
	@RequestMapping("/editItemSubmit")
	@RequiresPermissions("item:update")
	public String editItemSubmit(HttpServletRequest req) throws Exception {
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String createtime = req.getParameter("createtime");
		String price = req.getParameter("price");
		String pic = req.getParameter("pic");
		System.out.println(pic);
		String detail = req.getParameter("detail");
		SimpleDateFormat sdf = 
				new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date date = sdf.parse(createtime);
		Items items = new Items();
		items.setCreatetime(date);
		items.setDetail(detail);
		//TODO 图片修改
		items.setId(Integer.parseInt(id));
		items.setPrice(Float.parseFloat(price));
		itemService.updateByPrimaryKey(items);
		return "redirect:queryItems.action";
	}
}
