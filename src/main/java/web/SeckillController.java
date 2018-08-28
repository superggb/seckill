package web;


import dto.*;
import entity.Seckill;
import enums.SeckillStatEnum;
import exception.RepeatKillException;
import exception.SeckillCloseExcption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.SeckillService;

import java.util.Date;
import java.util.List;

@Controller//告诉配置文件这是一个控制组件
@RequestMapping("/seckill")//url:/模块/资源/{id}/细分  /seckill/list
public class SeckillController {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired//通过注解自动装配对象
    private SeckillService seckillService;

    //请求处理的方法中，参数可以加上model，其中包含了Map对象用来存储数据
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        //获取列表页
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list", list);
        //list.jsp+model=ModelAndView
        return "list";// /WEB-INF/jsp/"list".jsp
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }

        Seckill seckill = seckillService.getById(seckillId);

        if (seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    //ajax json
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExcecution> execute(@PathVariable("seckillId") Long seckillId,
                                                    @PathVariable("md5") String md5,
                                                    @CookieValue(value = "killPhone", required = false) Long phone) {
        if (phone == null) {
            return new SeckillResult<SeckillExcecution>(false, "未注册");
        }
        SeckillResult<SeckillExcecution> result;
        try {
            SeckillExcecution execution = seckillService.executeSeckill(seckillId, phone, md5);
            return new SeckillResult<SeckillExcecution>(true, execution);
        } catch (RepeatKillException e) {
            SeckillExcecution excecution = new SeckillExcecution(seckillId, SeckillStatEnum.REPEATE_KILL);
            return new SeckillResult<SeckillExcecution>(true, excecution);
        } catch (SeckillCloseExcption e) {
            SeckillExcecution excecution = new SeckillExcecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SeckillExcecution>(true, excecution);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            SeckillExcecution excecution = new SeckillExcecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<SeckillExcecution>(true, excecution);
        }
    }

    @RequestMapping(value="/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        System.out.println("fucktime");
        Date now=new Date();
        return new SeckillResult<Long>(true,now.getTime());
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public ggb test(@RequestParam(value = "name",required = false ) String name,@RequestParam(value = "msg",required = false) String msg){
        ggb ggb1=new ggb();
        if(name==null) {
            ggb1.setName("ggb");
            ggb1.setMsg("no name");
            System.out.println("name is null!");
            return ggb1;
        }
        if(msg ==null){
            ggb1.setName("gggb");
            ggb1.setMsg("no msg");
            System.out.println("msg is null!");
            return ggb1;
        }

        System.out.println("name="+name+"  msg="+msg);

        ggb1.setName(name);
        ggb1.setMsg(msg);
        return ggb1;
    }

    @RequestMapping(value = "/test2",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Logintest test2(@RequestParam(value = "name") String name, @RequestParam(value = "msg")String msg){
        System.out.println("name="+name+"  msg="+msg);
        Logintest logintest=new Logintest("this is openid","this is session_key");
        return logintest;
    }
}
