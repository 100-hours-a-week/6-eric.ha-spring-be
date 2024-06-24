package com.ktb.ktb_cj_community_spring_be.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 1552846012L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.ktb.ktb_cj_community_spring_be.global.entity.QBaseEntity _super = new com.ktb.ktb_cj_community_spring_be.global.entity.QBaseEntity(this);

    public final ListPath<com.ktb.ktb_cj_community_spring_be.comment.entity.Comment, com.ktb.ktb_cj_community_spring_be.comment.entity.QComment> comments = this.<com.ktb.ktb_cj_community_spring_be.comment.entity.Comment, com.ktb.ktb_cj_community_spring_be.comment.entity.QComment>createList("comments", com.ktb.ktb_cj_community_spring_be.comment.entity.Comment.class, com.ktb.ktb_cj_community_spring_be.comment.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> hits = createNumber("hits", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.ktb.ktb_cj_community_spring_be.global.util.aws.entity.PostImage, com.ktb.ktb_cj_community_spring_be.global.util.aws.entity.QPostImage> images = this.<com.ktb.ktb_cj_community_spring_be.global.util.aws.entity.PostImage, com.ktb.ktb_cj_community_spring_be.global.util.aws.entity.QPostImage>createList("images", com.ktb.ktb_cj_community_spring_be.global.util.aws.entity.PostImage.class, com.ktb.ktb_cj_community_spring_be.global.util.aws.entity.QPostImage.class, PathInits.DIRECT2);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final com.ktb.ktb_cj_community_spring_be.member.entity.QMember member;

    public final ListPath<PostLike, QPostLike> postLikes = this.<PostLike, QPostLike>createList("postLikes", PostLike.class, QPostLike.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.ktb.ktb_cj_community_spring_be.member.entity.QMember(forProperty("member")) : null;
    }

}

