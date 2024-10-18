package org.entropy.shortchain.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", OffsetDateTime.class,
                OffsetDateTime.now(ZoneOffset.ofHours(8)));
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
