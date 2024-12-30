package com.topBalance.wishTree.service;

import com.topBalance.wishTree.dto.WishTree;
import com.topBalance.wishTree.mapper.WishTreeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishTreeServiceImpl implements WishTreeService{

    @Autowired
    private WishTreeMapper wishTreeMapper;

    @Override
   public void insertWish(WishTree wishTree) {
        wishTreeMapper.insertWish(wishTree);

    }
}
