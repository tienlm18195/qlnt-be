package com.tainika.qlnt.qlnt.repository.customImpl;

import com.google.common.collect.Lists;
import com.tainika.qlnt.qlnt.dto.setting.user.UsersRequest;
import com.tainika.qlnt.qlnt.model.User;
import com.tainika.qlnt.qlnt.repository.UserRepositoryCustom;
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

public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private static final String USER_NAME_FIELD = "userName";
    private static final String FULL_NAME_FIELD = "fullName";
    private static final String EMAIL_FIELD = "email";
    private static final String PHONE_FIELD = "phone";
    private static final String IDENTITY_NUMBER_FIELD = "identityNumber";
    private static final String ADDRESS_NUMBER_FIELD = "address";
    private static final String IS_BLACK_LIST_FIELD = "isBlackList";
    private static final String STATUS_FIELD = "status";
    private static final String IS_DELETED_FIELD = "isDeleted";
    private static final String CREATE_TIME_FIELD = "createTime";
    private static final String UPDATE_TIME_FIELD = "updateTime";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public boolean isExistedUserName(String userName) {
        final Query query = new Query();
        Criteria criteria = Criteria
            .where(USER_NAME_FIELD)
            .regex(userName, "i");
        query.addCriteria(criteria);

        return this.mongoTemplate.count(query, User.class) > 0;
    }

    @Override
    public boolean isExistedEmail(String email) {
        final Query query = new Query();
        Criteria criteria = Criteria
            .where(USER_NAME_FIELD).is(email);
        query.addCriteria(criteria);

        return this.mongoTemplate.count(query, User.class) > 0;
    }

    @Override
    public List<User> searchAllUser(UsersRequest request) {
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
        return this.mongoTemplate.find(query, User.class);
    }

    public long countTotalUsersRecord(UsersRequest request) {
        final Query query = new Query();
        Criteria criteria = new Criteria();

        List<Criteria> criteriaList = getAllCriteriaByReqFilter(request);
        criteria.andOperator(criteriaList.toArray(new Criteria[0]));

        query.addCriteria(criteria);
        return this.mongoTemplate.count(query, User.class);
    }

    private List<Criteria> getAllCriteriaByReqFilter(UsersRequest request) {
        List<Criteria> criteriaList = Lists.newArrayList();
        if (isNotBlank(request.getFullName())) {
            criteriaList.add(
                Criteria.where(FULL_NAME_FIELD)
                    .regex(Pattern.compile(request.getFullName(), Pattern.CASE_INSENSITIVE))
            );
        }

        if (isNotBlank(request.getEmail())) {
            criteriaList.add(
                Criteria.where(EMAIL_FIELD)
                    .regex(Pattern.compile(request.getEmail(), Pattern.CASE_INSENSITIVE))
            );
        }

        if (isNotBlank(request.getPhone())) {
            criteriaList.add(
                Criteria.where(PHONE_FIELD)
                    .regex(Pattern.compile(request.getPhone(), Pattern.CASE_INSENSITIVE))
            );
        }

        if (isNotBlank(request.getIdentityNumber())) {
            criteriaList.add(
                Criteria.where(IDENTITY_NUMBER_FIELD)
                    .regex(Pattern.compile(request.getIdentityNumber(), Pattern.CASE_INSENSITIVE))
            );
        }

        if (isNotBlank(request.getAddress())) {
            criteriaList.add(
                Criteria.where(ADDRESS_NUMBER_FIELD)
                    .regex(Pattern.compile(request.getAddress(), Pattern.CASE_INSENSITIVE))
            );
        }

        if (request.getStatus() >= 0) {
            criteriaList.add(
                Criteria.where(STATUS_FIELD)
                    .is(request.getStatus())
            );
        }

        criteriaList.add(Criteria.where(IS_DELETED_FIELD).is(request.isDeleted()));
        criteriaList.add(Criteria.where(IS_BLACK_LIST_FIELD).is(request.isBlackList()));
        return criteriaList;
    }
}
