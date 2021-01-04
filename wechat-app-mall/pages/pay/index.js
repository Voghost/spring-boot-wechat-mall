// pages/pay/index.js
import { getSetting, openSetting, chooseAddress ,requestPayment,Payrequest,chkrequest,createOrder,showToast} from '../../utils/asyncWX'
import{request} from "../../request/index.js";
/**
 * 获取用户收货地址
 *  * 绑定点击事件
 *  * 获取用户对小程序所授予获取地址的权限状态scope
 *    * 假设用户点击获取收获地址的提示框 
 *      * 确定：scope为true authSetting: {scope.address: true} 直接获取收获地址
 *      * 取消：scope为false
 *        * 诱导用户自己打开授权设置页面(openSetting) 当用户重新给与获取地址权限的时候
 *        * 获取收货地址
 *    * 假设用户从未点击获取收获地址的提示框 
 *      * scope为undefined authSetting: {scope.address: undefined} 直接获取收获地址
 */
/**
 * 微信支付：
 *  * 哪些人 哪些账号 可以实现微信支付
 *    * 企业账号
 *    * 企业账号的小程序后台中必须给开发者添加上白名单
 */
Page({

  /**
   * 页面的初始数据
   */
  data: {
    address: {},
    goods: [],
    totalPrice: 0
  },
  onShow() {
    const address = wx.getStorageSync("address");
    const goods = wx.getStorageSync("payGoods");
    const totalPrice = goods.reduce((preValue, now) => {
      return preValue + now.goods_price * now.num
    }, 0)
    this.setData({
      address,
      goods,
      totalPrice
    })
  },
  async selectAddress() {
    try {
      // 1.获取用户对小程序所授予获取地址的权限状态scope
      const userSetting = await getSetting()
      const scopeAddress = userSetting.authSetting["scope.address"]
      // 2.判断权限状态
      if (scopeAddress === false) {
        // 用户拒接过授予权限，诱导用户打开授权界面
        await openSetting();
      }
      // 3.调用获取地址API
      const address = await chooseAddress();
      wx.setStorageSync("address", address);
    } catch (error) {
      console.log(error)
    }
  },
  async handlePay(){
    
 try{
  
  const token=wx.getStorageSync("token");
  const isChecked=wx.getStorageSync("isChecked")
  const address=wx.getStorageSync("address")
  if(!token){
    wx.navigateTo({
      url: '/pages/login/login',
    });
    return;
  }
  if(!address.userName)
  {
    await showToast({title:"您还没有选择收货地址"});
    return;
  }

const header={Authorization:token};

const order_price=this.data.totalPrice;
const consignee_addr=this.data.address;
const cart=this.data.goods;
let goods=[];
cart.forEach(v=>goods.push({
goods_id:v.goods_id,
goods_number:v.num,
goods_price:v.goods_price
}))
//创建订单
const orderParams={order_price,consignee_addr,goods};
console.log("orderParams:"+orderParams);
const {order_number}= await request({
url: "/my/orders/create",
data: orderParams,
header:header,
method: "POST",
});
// const {order_number}=createOrder(orderParams,header);
console.log("order_number:"+order_number);
//发起预支付接口
const {pay} =await request({url:"/my/orders/pay",method:"POST",header:header,data:{order_number}})
// const {pay}=Payrequest(order_number,header);
console.log("pay:"+pay);
//发起微信支付
// const {pay}=await requestPayment(pay);
//查询订单状态
// const res=await request({url:"/my/orders/chkOrder",method:"POST",header:header,data:{order_number}})
// const res=await chkrequest(order_number,header);
await wx.showToast({title:"支付成功"});
//删除购物车缓存
let newCart=wx.getStorageSync("cart");
newCart=newCart.filter(v=>!v.isChecked);
wx.setStorageSync("cart", newCart);

//返回订单页面
wx.navigateTo({
url: "/pages/order/index?type=1",
})

 }
 catch(error)
 { await wx.showToast({title:"支付失败"});
  console.log(error);
 }

    // wx.showToast({
    //   title: '暂时不支持支付功能~',
    //   icon: 'none',
    //   mask: true
    // });
  }
})