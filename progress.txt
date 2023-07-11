	角色头像的图片大小最好为60*60
	结算界面头像大小 360*360
	
	开发记录/规划（6/6开始）：
	程序的框架设计，和基本方法	完成，耗时2天（6/8）
	完成hero之间的基本交互设计， 攻击，合作（需要增加hero的属性：视野范围、攻击范围、攻击对象hero组、合作对象hero组	完成，耗时一下午（6/8）
	CPanel（角色菜单）血条上显示数字	完成（6/8
	文字滚动条增加提示文字basic 完成（6/8
	完善游戏机制	完成（6/9
	游戏胜利提示	完成 （6/11
	优化AI	基本完成（6/11
	增加10-15个独特hero继承hero抽象类，将这些hero存入data中的hero database	完成一半（6/11  完成（6/12
	设置每个hero特殊技能	基本完成
	把food图片改成水果的样子	不做了
	文字滚动条增加提示文字advance	基本完成
	受伤Cpanel闪红	不做了
	设计地图（指背景图片）	基本完成
	完善场景，增加夜晚和白天改变hero视野大小	不做了
	已知可以做的代码优化（1.在英雄死掉的时候把对应的C panel移除更新组 2.hero中的检查位置合法性不用魔法数 3.hero和food共同继承坐标类）懒得做
	完善地图效果	不可能完成
	背景音乐	完成（6/8
	改变绝对布局（我觉得不可能完成
	
	6/9 update:
	+ 增加了rehp这一属性
	+ 增加了food这一游戏机制，加到Data里和对应的MapPanel里，并在hero里增加对应的方法：
		Data	foodList
		Hero	detectFood()
		MapPanel	paintComponent()
		GameMainThread	haveSomeFood()
	+ 同时为 hero 增加饥饿度这一属性（继承的类没有特殊情况不需要修改该属性），如果过低就会掉血，并进入狂暴模式
		Hero	hungry,maxHungry	//maxHungry可以在类中修改，不过特殊的有一个就行
	+ 增加了ScrollText里相应的输出方式
	+ 新英雄Suzuha
	+ 用于触发技能方便，加了以下属性
		Hero 	isAttackRange
		
	6/10 update：
	+ 增加了 C panel饥饿度的显示
	
	6/11 update:
	+ 眩晕计时器
	+ 为了设置技能方便，增加了hero的状态检查方法，可以在被眩晕时调用
	+ 重写了英雄攻击的机制，英雄在攻击前会把可以攻击的英雄加入可攻击列表，方便子类调用
	+ 新英雄Aria
	+ 新英雄Sakuya
	+ 上调所有人的武器cd =*4
	+ 结算画面
	+ 为了加快游戏速度，更改了部分游戏规则
	+ 随便去网上下了个地图
	
	6/12 update:
	+ Hero类  胜利宣言,在结算画面显示一句话
	+ HeroDatabase 存储所有英雄的信息，胜场和参与
	+ mapPanel 当hero之间攻击时连线表示
		