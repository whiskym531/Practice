package com.example.carrot.autoconfig.redis;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "redis"
)
public class RedisProperties {
    private String auth;
    private int port = 6379;
    private int maxTotal = 10;
    private int maxIdle = 0;
    private int maxWaitMillis = 10000;
    private boolean testOnBorrow = true;
    private boolean cluster = false;
    private String host;
    private String sentinelHosts;
    private String sentinelMasterName;

    public RedisProperties() {
    }

    public String getAuth() {
        return this.auth;
    }

    public int getPort() {
        return this.port;
    }

    public int getMaxTotal() {
        return this.maxTotal;
    }

    public int getMaxIdle() {
        return this.maxIdle;
    }

    public int getMaxWaitMillis() {
        return this.maxWaitMillis;
    }

    public boolean isTestOnBorrow() {
        return this.testOnBorrow;
    }

    public boolean isCluster() {
        return this.cluster;
    }

    public String getHost() {
        return this.host;
    }

    public String getSentinelHosts() {
        return this.sentinelHosts;
    }

    public String getSentinelMasterName() {
        return this.sentinelMasterName;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public void setCluster(boolean cluster) {
        this.cluster = cluster;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setSentinelHosts(String sentinelHosts) {
        this.sentinelHosts = sentinelHosts;
    }

    public void setSentinelMasterName(String sentinelMasterName) {
        this.sentinelMasterName = sentinelMasterName;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof RedisProperties)) {
            return false;
        } else {
            RedisProperties other = (RedisProperties)obj;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$auth = this.getAuth();
                Object other$auth = other.getAuth();
                if (this$auth == null) {
                    if (other$auth != null) {
                        return false;
                    }
                } else if (!this$auth.equals(other$auth)) {
                    return false;
                }

                if (this.getPort() != other.getPort()) {
                    return false;
                } else if (this.getMaxTotal() != other.getMaxTotal()) {
                    return false;
                } else if (this.getMaxIdle() != other.getMaxIdle()) {
                    return false;
                } else if (this.getMaxWaitMillis() != other.getMaxWaitMillis()) {
                    return false;
                } else if (this.isTestOnBorrow() != other.isTestOnBorrow()) {
                    return false;
                } else if (this.isCluster() != other.isCluster()) {
                    return false;
                } else {
                    Object this$host = this.getHost();
                    Object other$host = other.getHost();
                    if (this$host == null) {
                        if (other$host != null) {
                            return false;
                        }
                    } else if (!this$host.equals(other$host)) {
                        return false;
                    }

                    Object this$sentinelHosts = this.getSentinelHosts();
                    Object other$sentinelHosts = other.getSentinelHosts();
                    if (this$sentinelHosts == null) {
                        if (other$sentinelHosts != null) {
                            return false;
                        }
                    } else if (!this$sentinelHosts.equals(other$sentinelHosts)) {
                        return false;
                    }

                    Object this$sentinelMasterName = this.getSentinelMasterName();
                    Object other$sentinelMasterName = other.getSentinelMasterName();
                    if (this$sentinelMasterName == null) {
                        if (other$sentinelMasterName != null) {
                            return false;
                        }
                    } else if (!this$sentinelMasterName.equals(other$sentinelMasterName)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    public boolean canEqual(Object other) {
        return other instanceof RedisProperties;
    }

    public int hashCode() {
        Boolean PRIME = true;
        int result = 1;
        Object $auth = this.getAuth();
        result = result * 31 + ($auth == null ? 0 : $auth.hashCode());
        result = result * 31 + this.getPort();
        result = result * 31 + this.getMaxTotal();
        result = result * 31 + this.getMaxIdle();
        result = result * 31 + this.getMaxWaitMillis();
        result = result * 31 + (this.isTestOnBorrow() ? 1231 : 1237);
        result = result * 31 + (this.isCluster() ? 1231 : 1237);
        Object $host = this.getHost();
        result = result * 31 + ($host == null ? 0 : $host.hashCode());
        Object $sentinelHosts = this.getSentinelHosts();
        result = result * 31 + ($sentinelHosts == null ? 0 : $sentinelHosts.hashCode());
        Object $sentinelMasterName = this.getSentinelMasterName();
        result = result * 31 + ($sentinelMasterName == null ? 0 : $sentinelMasterName.hashCode());
        return result;
    }

    public String toString() {
        return "RedisProperties(auth=" + this.getAuth() + ", port=" + this.getPort() + ", maxTotal=" + this.getMaxTotal() + ", maxIdle=" + this.getMaxIdle() + ", maxWaitMillis=" + this.getMaxWaitMillis() + ", testOnBorrow=" + this.isTestOnBorrow() + ", cluster=" + this.isCluster() + ", host=" + this.getHost() + ", sentinelHosts=" + this.getSentinelHosts() + ", sentinelMasterName=" + this.getSentinelMasterName() + ")";
    }
}
