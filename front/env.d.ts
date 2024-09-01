/// <reference types="vite/client" />
interface ImportMetaEnv {
  readonly VITE_APP_MAIN_SERVER: string;
  // 추가적인 환경 변수가 있다면 여기에 추가
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}