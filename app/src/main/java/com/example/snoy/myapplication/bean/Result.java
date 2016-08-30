package com.example.snoy.myapplication.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/25.
 */
public class Result {


    private int count;
    private int err;
    private int total;
    private int page;
    private int refresh;

    private List<ItemsBean> items;

    public static class ItemsBean {
        private String format;
        private Object image;
        private int published_at;
        private String tag;
        /**
         * avatar_updated_at : 1471345423
         * uid : 12173087
         * last_visited_at : 1382862710
         * created_at : 1382862710
         * state : bonded
         * last_device : ios_2.6.1
         * role : n
         * login : 云栈洞总书记
         * id : 12173087
         * icon : 2016081611034324.JPEG
         */

        private UserBean user;
        private Object image_size;
        private int id;
        /**
         * status : publish
         * user_id : 30878788
         * floor : 19
         * ip : 211.91.181.57
         * created_at : 2016-08-25 10:17:51
         * comment_id : 363968465
         * like_count : 98
         * pos : 0
         * content : 后来那个老师怀孕了，老师说是楼主同桌的孩子；楼主同桌说:“小书也操了，怎么不说是他的”老师看了楼主一眼语重心长道:“他找不到洞！”快扶我上神评
         * source : android
         * score : null
         * parent_id : 0
         * anonymous : 0
         * neg : 0
         * article_id : 117367265
         * user : {"avatar_updated_at":1467034891,"uid":30878788,"last_visited_at":1451560548,"created_at":1451560548,"state":"active","last_device":"android_9.0.0","role":"n","login":"露气冲冲","id":30878788,"icon":"20160627134130.jpg"}
         */

        private HotCommentBean hot_comment;
        /**
         * down : -85
         * up : 4285
         */

        private VotesBean votes;
        private int created_at;
        private String content;
        private String state;
        private int comments_count;
        private boolean allow_comment;
        private int share_count;
        private String type;



        public static class UserBean {
            private int avatar_updated_at;
            private int uid;
            private int last_visited_at;
            private int created_at;
            private String state;
            private String last_device;
            private String role;
            private String login;
            private int id;
            private String icon;

            public int getAvatar_updated_at() {
                return avatar_updated_at;
            }

            public void setAvatar_updated_at(int avatar_updated_at) {
                this.avatar_updated_at = avatar_updated_at;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getLast_visited_at() {
                return last_visited_at;
            }

            public void setLast_visited_at(int last_visited_at) {
                this.last_visited_at = last_visited_at;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getLast_device() {
                return last_device;
            }

            public void setLast_device(String last_device) {
                this.last_device = last_device;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getLogin() {
                return login;
            }

            public void setLogin(String login) {
                this.login = login;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }

        public static class HotCommentBean {
            private String status;
            private int user_id;
            private int floor;
            private String ip;
            private String created_at;
            private int comment_id;
            private int like_count;
            private int pos;
            private String content;
            private String source;
            private Object score;
            private int parent_id;
            private int anonymous;
            private int neg;
            private int article_id;
            /**
             * avatar_updated_at : 1467034891
             * uid : 30878788
             * last_visited_at : 1451560548
             * created_at : 1451560548
             * state : active
             * last_device : android_9.0.0
             * role : n
             * login : 露气冲冲
             * id : 30878788
             * icon : 20160627134130.jpg
             */

            private UserBean user;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getFloor() {
                return floor;
            }

            public void setFloor(int floor) {
                this.floor = floor;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public int getComment_id() {
                return comment_id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
            }

            public int getLike_count() {
                return like_count;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public int getPos() {
                return pos;
            }

            public void setPos(int pos) {
                this.pos = pos;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public Object getScore() {
                return score;
            }

            public void setScore(Object score) {
                this.score = score;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }

            public int getAnonymous() {
                return anonymous;
            }

            public void setAnonymous(int anonymous) {
                this.anonymous = anonymous;
            }

            public int getNeg() {
                return neg;
            }

            public void setNeg(int neg) {
                this.neg = neg;
            }

            public int getArticle_id() {
                return article_id;
            }

            public void setArticle_id(int article_id) {
                this.article_id = article_id;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public static class UserBean {
                private int avatar_updated_at;
                private int uid;
                private int last_visited_at;
                private int created_at;
                private String state;
                private String last_device;
                private String role;
                private String login;
                private int id;
                private String icon;

                public int getAvatar_updated_at() {
                    return avatar_updated_at;
                }

                public void setAvatar_updated_at(int avatar_updated_at) {
                    this.avatar_updated_at = avatar_updated_at;
                }

                public int getUid() {
                    return uid;
                }

                public void setUid(int uid) {
                    this.uid = uid;
                }

                public int getLast_visited_at() {
                    return last_visited_at;
                }

                public void setLast_visited_at(int last_visited_at) {
                    this.last_visited_at = last_visited_at;
                }

                public int getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(int created_at) {
                    this.created_at = created_at;
                }

                public String getState() {
                    return state;
                }

                public void setState(String state) {
                    this.state = state;
                }

                public String getLast_device() {
                    return last_device;
                }

                public void setLast_device(String last_device) {
                    this.last_device = last_device;
                }

                public String getRole() {
                    return role;
                }

                public void setRole(String role) {
                    this.role = role;
                }

                public String getLogin() {
                    return login;
                }

                public void setLogin(String login) {
                    this.login = login;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }
            }
        }

        public static class VotesBean {
            private int down;
            private int up;

            public int getDown() {
                return down;
            }

            public void setDown(int down) {
                this.down = down;
            }

            public int getUp() {
                return up;
            }

            public void setUp(int up) {
                this.up = up;
            }
        }
    }
}
