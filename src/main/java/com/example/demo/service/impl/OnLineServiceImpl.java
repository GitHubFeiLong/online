package com.example.demo.service.impl;

import com.example.demo.config.MemberVariable;
import com.example.demo.dao.OnLineDao;
import com.example.demo.entry.dto.DWZDTO;
import com.example.demo.enumerate.SystemExceptionEnum;
import com.example.demo.exception.SystemException;
import com.example.demo.service.OnLineService;
import com.example.demo.util.GetNowUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类描述：
 * 短网址，具体业务类
 * @ClassName OnLineServiceImpl
 * @Description TODO
 * @Author msi
 * @Date 2020/8/20 13:45
 * @Version 1.0
 */
@Service
public class OnLineServiceImpl extends MemberVariable implements OnLineService {

    @Autowired
    private OnLineDao onLineDao;

    /**
     * redis key
     */
    public static final String redisKey = "online:dwz";
    /**
     * 添加一天短网址
     *
     * @param dwzdto
     * @return
     */
    @Override
    public Integer saveDWZ(DWZDTO dwzdto) throws SystemException {

        // 保存去掉换行符的字符串
        dwzdto.setCwz(dwzdto.getCwz().replaceAll("\n",""));

        // 短网址保存
        String dwz = "/" + RandomStringUtils.randomAlphanumeric(5);
        String http = super.httpServletRequest.getScheme();
        String serverName = super.httpServletRequest.getServerName();
        int port = super.httpServletRequest.getServerPort();
        StringBuilder sb = new StringBuilder();
        sb.append(http).append("://").append(serverName).append(":").append(port).append(dwz);

        dwzdto.setDwz(sb.toString());
        dwzdto.setCreateTime(GetNowUtil.getDateTime());
        dwzdto.setModifiedTime(GetNowUtil.getDateTime());

        // 保存数据库
        Integer count = onLineDao.insertDWZ(dwzdto);

        // 更新redis
        super.redisUtil.del(OnLineServiceImpl.redisKey);
        List<DWZDTO> listDWZ = this.onLineDao.listDWZ();
        super.redisUtil.set(OnLineServiceImpl.redisKey, listDWZ, 300);

        if (count <= 0) {
            throw new SystemException(SystemExceptionEnum.DB_INSERT_ERROR);
        }
        return count;
    }

    /**
     * 查询
     * @return
     */
    @Override
    public PageInfo<DWZDTO> selectCwz() {
        List<DWZDTO> listDWZ = (List<DWZDTO>)super.redisUtil.get(OnLineServiceImpl.redisKey);

        if (listDWZ == null || listDWZ.isEmpty()) {
            listDWZ = this.onLineDao.listDWZ();
            super.redisUtil.set(OnLineServiceImpl.redisKey, listDWZ, 300);
        }
        PageInfo<DWZDTO> dwzdtoPageInfo = new PageInfo<>(listDWZ);
        return dwzdtoPageInfo;
    }

    /**
     * 删除网址
     * @param id
     * @return
     */
    @Override
    public Integer removeCwz(Integer id) throws SystemException {
        Integer count = this.onLineDao.deleteCwz(id);
        if (count <= 0) {
            throw new SystemException(SystemExceptionEnum.DB_DELETE_ERROR);
        }
        super.redisUtil.del(OnLineServiceImpl.redisKey);
        return count;
    }
}
