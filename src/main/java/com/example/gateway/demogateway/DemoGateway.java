package com.example.gateway.demogateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoGateway {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 特定のパス(/get)が来たら指定のURLにルーティングする
                .route("basic", r ->
                        r.path("/get")
                                .uri("http://httpbin.org"))
                // predicate例(特定HTTPヘッダーが含まれているか)
                // curl -H "x-demo-header: demo-spring-cloud-gateway" http://localhost:8080/json
                .route("predicate-header", r ->
                        r.path("/json")
                                .and()
                                .header("x-demo-header", "demo-spring-cloud-gateway")
                                .uri("http://httpbin.org"))

                // filter例
                // /hogehogeで来たパスを/searchに書き換えてgithubにルーティングする
                // http://localhost:8080/hogehoge?q=spring
                .route("filter-rewrite",
                        r -> r.path("/hogehoge")
                                .filters(f -> f.rewritePath("/.*", "/search"))
                .uri("https://github.com/"))

                // 重み付け(荷重ルーティング)例
                // 対象エンドポイントをグループID(group-1)でグルーピングし重み付けをする。
                // http://localhost:8080
                .route("weight1", r ->
                        r.path("/")
                                .and()
                                .weight("group-1", 5)
                                .uri("https://httpbin.org"))
                .route("weight2", r ->
                        r.path("/")
                                .and()
                                .weight("group-1", 5)
                                .uri("https://github.com/")
                ).build();
    }
}
