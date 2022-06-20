import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:'/user',
    meta: {
      title: '后台'
    },
    children:[
      {
        path:'/user',
        name:'User',
        component:() => import("@/views/User")
      },
      {
        path: '/person',
        name: 'Person',
        component: () => import("@/views/Person")
      },
      {
        path: '/article',
        name: 'Article',
        component: () => import("@/views/Article")
      },
      {
        path: '/comment',
        name: 'Comment',
        component: () => import("@/views/Comment")
      },

    ]
  },

  {
  path: '/login',
    name: 'Login',
    component: () => import("@/views/Login")
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import("@/views/Register")
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})
router.beforeEach((to, from, next) => {
  // NProgress.start();
  if (to.path == "/login") {
    next();
  } else if (
      JSON.stringify(sessionStorage.getItem("user"))=="null"
  ) {

    next('/login');
  } else {
    next();
  }
});

export default router
