package com.xiaoju.framework;

import com.alibaba.fastjson.JSONObject;
import com.xiaoju.framework.controller.CaseController;
import com.xiaoju.framework.entity.request.cases.CaseCreateReq;
import com.xiaoju.framework.entity.request.cases.CaseDeleteReq;
import com.xiaoju.framework.entity.request.cases.CaseEditReq;
import com.xiaoju.framework.entity.request.ws.WsSaveReq;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


/**
 * Created by yimfeng on 2021/11/22.
 */
public class CaseControllerTests extends CaseServerTest {
	@Autowired
	private CaseController caseController;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(caseController).build();
	}

	@Test
	public void exportTest1() throws Exception  {

		// 测试list接口 put方法
		MvcResult mvcResultList = mockMvc.perform(MockMvcRequestBuilders.get("/api/case/list")
				.accept(MediaType.APPLICATION_JSON).param("channel", "1")
				.accept(MediaType.APPLICATION_JSON).param("productLineId", "1")
				.accept(MediaType.APPLICATION_JSON).param("bizId", "-1")
				.accept(MediaType.APPLICATION_JSON).param("title", "done测试")
				.accept(MediaType.APPLICATION_JSON).param("creator", "yimfeng")
				.accept(MediaType.APPLICATION_JSON).param("requirementId", "")
				.accept(MediaType.APPLICATION_JSON).param("beginTime", "111")
				.accept(MediaType.APPLICATION_JSON).param("endTime", "222")
				.accept(MediaType.APPLICATION_JSON).param("pageNum", "1")
				.accept(MediaType.APPLICATION_JSON).param("pageSize", "10"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		String contentList = mvcResultList.getResponse().getContentAsString();
		JSONObject jsonList = JSONObject.parseObject(contentList);
		System.out.println("data中的内容为 : " + jsonList.getString("data"));


		// 测试create接口 post方法
		CaseCreateReq caseCreateReq = new CaseCreateReq();
		caseCreateReq.setCreator("yimfeng");
		caseCreateReq.setProductLineId(1L);
		caseCreateReq.setCaseType(0);
		caseCreateReq.setCaseContent("{\"root\":{\"data\":{\"id\":\"bv8nxhi3c800\",\"created\":1562059643204,\"text\":\"中心主题\"},\"children\":[]},\"template\":\"default\",\"theme\":\"fresh-blue\",\"version\":\"1.4.43\",\"base\":0}");
		caseCreateReq.setTitle("copy of done测试");
		caseCreateReq.setChannel(1);
		caseCreateReq.setBizId("-1");
		caseCreateReq.setId(2483L);
		caseCreateReq.setRequirementId("111");
		caseCreateReq.setDescription("这是个测试");

		MvcResult mvcResultCreate = mockMvc.perform(MockMvcRequestBuilders.post("/api/case/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSONObject.toJSONString(caseCreateReq)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		String contentCreate = mvcResultCreate.getResponse().getContentAsString();
		JSONObject jsonCreate = JSONObject.parseObject(contentCreate);
		System.out.println("data中的内容为 : " + jsonCreate.getString("data"));

		Long id = Long.valueOf(jsonCreate.getString("data"));

		// 测试edit接口 post方法
		CaseEditReq caseEditReq = new CaseEditReq();
		caseEditReq.setId(id);
		caseEditReq.setCaseType(0);
		caseEditReq.setTitle("done测试");
		caseEditReq.setModifier("yimfeng");
		caseEditReq.setBizId("-1");
		caseEditReq.setChannel(1);
		caseEditReq.setRequirementId("111");
		caseEditReq.setDescription("编辑用例集");


		MvcResult mvcResultEdit = mockMvc.perform(MockMvcRequestBuilders.post("/api/case/edit")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSONObject.toJSONString(caseEditReq)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		String contentEdit = mvcResultEdit.getResponse().getContentAsString();
		JSONObject jsonEdit = JSONObject.parseObject(contentEdit);
		System.out.println("data中的内容为 : " + jsonEdit.getString("data"));


		// 测试delete接口 post方法
		CaseDeleteReq caseDeleteReq = new CaseDeleteReq();
		caseDeleteReq.setId(id);
		MvcResult mvcResultDelete = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/case/delete")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSONObject.toJSONString(caseDeleteReq)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		String contentDelete = mvcResultDelete.getResponse().getContentAsString();
		JSONObject jsonDelete = JSONObject.parseObject(contentDelete);
		System.out.println("data中的内容为 : " + jsonDelete.getString("data"));



		// 测试listCreators接口 get方法
		MvcResult mvcResultCreators = mockMvc.perform(MockMvcRequestBuilders.get("/api/case/listCreators")
				.accept(MediaType.APPLICATION_JSON).param("caseType", "0")
				.accept(MediaType.APPLICATION_JSON).param("productLineId", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		String contentCreators = mvcResultCreators.getResponse().getContentAsString();
		JSONObject jsonCreators = JSONObject.parseObject(contentCreators);
		System.out.println("data中的内容为 : " + jsonCreators.getString("data"));


		// 测试CastInfo接口 get方法
		MvcResult mvcResultInfo = mockMvc.perform(MockMvcRequestBuilders.get("/api/case/getCaseInfo")
				.accept(MediaType.APPLICATION_JSON).param("id", "2483"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isNotEmpty())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		String contentInfo = mvcResultInfo.getResponse().getContentAsString();
		JSONObject jsonInfo = JSONObject.parseObject(contentInfo);
		System.out.println("data中的内容为 : " + jsonInfo.getString("data"));


		// 测试update接口 post方法
		WsSaveReq wsSaveReq = new WsSaveReq();
		wsSaveReq.setId(id);
		wsSaveReq.setCaseContent("{\"root\":{\"data\":{\"created\":1562059643204,\"id\":\"bv8nxhi3c800\",\"text\":\"中心主题\"},\"children\":[]},\"template\":\"default\",\"theme\":\"fresh-blue\",\"version\":\"1.4.43\",\"base\":1}");
		wsSaveReq.setModifier("yimfeng");
		wsSaveReq.setRecordId(null);
		MvcResult mvcResultUpdate = mockMvc.perform(MockMvcRequestBuilders.post("/api/case/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSONObject.toJSONString(wsSaveReq)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();

		String contentUpdate = mvcResultUpdate.getResponse().getContentAsString();
		JSONObject jsonUpdate = JSONObject.parseObject(contentUpdate);
		System.out.println("data中的内容为 : " + jsonUpdate.getString("data"));
	}

}
