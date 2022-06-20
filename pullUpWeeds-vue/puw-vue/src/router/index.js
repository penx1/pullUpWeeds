import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:'/mainPage',
    meta: {
      title: 'puw'
    },
    children:[
      {
        path: '/person',
        name: 'Person',
        component: () => import("@/views/Person")
      },
      {
        path: '/mainPage',
        name: 'MainPage',
        component: () => import("@/views/MainPage")
      },
      {
        path: '/detailedArticle/:id',
        name: 'DetailedArticle',
        component: () => import("@/views/DetailedArticle")
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
  if (to.path == "/login" || to.path == "/register") {
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
