/**
 * promise形式的 getsetting
 */
export const getSetting = () => {
  return new Promise((resolve, reject) => {
    wx.getSetting({
      success: (result) => {
        resolve(result)
      },
      fail: (err) => {
        reject(err)
      }
    });
  })
}
/**
 * promise形式的 chooseAddress
 */
export const chooseAddress = () => {
  return new Promise((resolve, reject) => {
    wx.chooseAddress({
      success: (result) => {
        resolve(result)
      },
      fail: (err) => {
        reject(err)
      }
    });
  })
}
/**
 * promise形式的 openSetting
 */
export const openSetting = () => {
  return new Promise((resolve, reject) => {
    wx.openSetting({
      success: (result) => {
        resolve(result)
      },
      fail: (err) => {
        reject(err)
      }
    });
  })
}

/*
登录
*/
export const login = () => {
  return new Promise((resolve, reject) => {
    wx.login({
      timeout: 10000,
      success:(result)=>{
        resolve(result);
      },
      fail:(err)=>{
        reject(err);
      },

    });
  })
}
/*
*支付参数
*@param {object} pay
*/
export const requestPayment = (pay) => {
  return new Promise((resolve, reject) => {
    wx.requestPayment({
     ...pay,
success:(result)=>{
  resolve(result);
},
fail:(err)=>{
  reject(err);
}
    })
  })
}

export const Payrequest = (order_number,header) => {
  return new Promise((resolve, reject) => {
    wx.request({
      url:"http://localhost:8080/wechatapi/my/orders/pay",method:"POST",header:header,data:{order_number},

success:(result)=>{
  resolve(result);
},
fail:(err)=>{
  reject(err);
}
    })
  })
}

export const chkrequest = (order_number,header) => {
  return new Promise((resolve, reject) => {
    wx.request({
      url:"http://localhost:8080/wechatapi/my/orders/chkOrder",method:"POST",header:header,data:{order_number},

success:(result)=>{
  resolve(result);
},
fail:(err)=>{
  reject(err);
}
    })
  })
}

export const ordersall = (header) => {
  return new Promise((resolve, reject) => {
    wx.request({
      url:"http://localhost:8080/wechatapi/my/orders/all",method:"POST",header:header,

success:(result)=>{
  resolve(result);
},
fail:(err)=>{
  reject(err);
}
    })
  })
}

export const createOrder = (orderParams,header) => {
  return new Promise((resolve, reject) => {
    wx.request({
      url:"http://localhost:8080/wechatapi/my/orders/create",method:"POST",header:header,data:{orderParams},
success:(result)=>{
  resolve(result);
},
fail:(err)=>{
  reject(err);
}
    })
  })
}

export const showToast = ({title}) => {
  return new Promise((resolve, reject) => {
    wx.showToast({
      title: title,
      icon:'none',
      success:(result)=>{
        resolve(result);
},
      fail:(err)=>{
        reject(err);
}
    })
  })
}