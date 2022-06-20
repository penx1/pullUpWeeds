import App from './App.vue'
import router from './router'
import store from './store'
import './assets/css/global.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import {createApp} from "@vue/runtime-dom";
createApp(App).use(store).use(router).use(ElementPlus, {locale: zhCn
    // ,size: 'small'
}).mount('#app')

router.beforeEach((to, from, next) => {
    // NProgress.start();
    if (to.path == "/login") {
        next();
    } else if (JSON.stringify( sessionStorage.getItem("user"))=='{}' || sessionStorage.getItem("user") =="null" ) {
        next('/login');
    } else {
        next();
    }
});

