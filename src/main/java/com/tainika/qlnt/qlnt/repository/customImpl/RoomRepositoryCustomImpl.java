package com.tainika.qlnt.qlnt.repository.customImpl;

import com.google.common.collect.Lists;
import com.tainika.qlnt.qlnt.dto.setting.room.RoomsRequest;
import com.tainika.qlnt.qlnt.model.Room;
import com.tainika.qlnt.qlnt.repository.RoomRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.regex.Pattern;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

public class RoomRepositoryCustomImpl implements RoomRepositoryCustom {

    private static final String NAME_FIELD = "name";
    private static final String PRICE_FIELD = "price";
    private static final String STATUS_FIELD = "status";
    private static final String IS_DELETED_FIELD = "isDeleted";
    private static final String CREATE_TIME_FIELD = "createTime";
    private static final String UPDATE_TIME_FIELD = "updateTime";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Room> searchAllRooms(RoomsRequest request) {
        final Query query = new Query();
        Criteria criteria = new Criteria();

        Pageable pageable = PageRequest.of(
            request.getPage(),
            request.getSize(),
            Sort.Direction.DESC,
            UPDATE_TIME_FIELD, CREATE_TIME_FIELD
        );
        query.with(pageable);

        List<Criteria> criteriaList = getAllCriteriaByReqFilter(request);
        criteria.andOperator(criteriaList.toArray(new Criteria[0]));
        query.addCriteria(criteria);
        return this.mongoTemplate.find(query, Room.class);
    }

    @Override
    public long countSearchAllRooms(RoomsRequest request) {
        final Query query = new Query();
        Criteria criteria = new Criteria();

        List<Criteria> criteriaList = getAllCriteriaByReqFilter(request);
        criteria.andOperator(criteriaList.toArray(new Criteria[0]));
        query.addCriteria(criteria);
        return this.mongoTemplate.count(query, Room.class);
    }

    private List<Criteria> getAllCriteriaByReqFilter(RoomsRequest request) {
        List<Criteria> criteriaList = Lists.newArrayList();

        if (isNotBlank(request.getName())) {
            criteriaList.add(Criteria.where(NAME_FIELD)
                .regex(Pattern.compile(request.getName(), Pattern.CASE_INSENSITIVE))
            );
        }

        if (request.getStatus() >= 0) {
            criteriaList.add(Criteria.where(STATUS_FIELD)
                .is(request.getStatus())
            );
        }

        criteriaList.add(Criteria.where(IS_DELETED_FIELD).is(request.isDeleted()));
        return criteriaList;
    }
}
