# PocketAsk
口袋问

#使用
简单的更新和使用说明
##标题栏
标题栏，默认mode为PERSON_TITLE_EDIT，显示效果是个人－标题－编辑 
* PERSON_TITLE_EDIT，显示效果是个人－标题－编辑
* BACK-TITLE-GO 左边返回-中间标题-右边前进的样式
* BACK-TITLE 左边返回－中间标题

按钮的点击事件需要监听TitleViewListener
##可拖动的卡片
完全自定义实现了卡片的拖动和背景的缩放效果，可以设置最大的卡片数量。卡片的不同地方点击效果会不一样，需要实现OnCardSlidingListener监听卡片的拖动、移除和点击
##单选和多选
自定义的单选喝多选的选项控件,使用方法,在布局文件里面添加一个SelectorAdapter作为选项的父布局,设置SelectorMode类型,有
* [RadioButton]单选
* [CheckButton]多选。

需要先执行init函数实现SelectorCheckListener方法监听点击的选择事件。添加数据的方式通listView，直接setAdapter
##开关
类型：
按钮的两种通过crb_mode选择
* 0 任意 附近
* 1 公开 非公开

位置：
* left_padding 相对于父物体的左边距
* top_padding 相对于父物体的上边距

开关事件：
调用setCRListener
重写CRListener openState() closeState方
##定位管理 LocationManager
new的时候就会自动开始定位，使用destory销毁，不用的时候必须销毁
##MyQAAdapter
我的提问和我的回答的列表适配器，通过设置QAType设置类型
##RecordManager 录音管理
* startRecord(String name) 开始录音，name是保存的文件名
* stopRecord 结束录音
* startPlay(String filePath) 开始播放录音 filepath是文件路径
* stopPlay 结束播放
* startRecordTemp() 封装的保存录音到临时目录
* startPlayTemp() 播放临时的录音文件

##PasswordUtil 密码加密工具
* getSampleEncodePassword(String password) 获取经过简单md5加密的密码，只进行一次加密
* getDoubleEncodePassword(String password) 获取经过两次md5加密的密码
* getEncodeUsernamePassword(String username, String password) 获取经过用户名和密码双重加密的密码

