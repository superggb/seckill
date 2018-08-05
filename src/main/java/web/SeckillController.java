package web;


import dto.Exposer;
import dto.SeckillExcecution;
import dto.SeckillResult;
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

    @RequestMapping(value="/{seckillId}/{md5}/execution",
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
        Date now=new Date();
        return new SeckillResult<Long>(true,now.getTime());
    }
}
