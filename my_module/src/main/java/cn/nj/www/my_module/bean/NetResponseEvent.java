package cn.nj.www.my_module.bean;

public class NetResponseEvent extends BaseResponse
{
    public enum Cache {
        isCache//缓存数据
        ,isNetWork//网络数据
    }
    private String result;
    
    private String tag;

    private Cache cache;//是否是缓存数据
    
    public NetResponseEvent(String result, String tag,Cache cache)
    {
        setResult(result);
        setTag(tag);
        setCache(cache);
    }

    public String getTag()
    {
        return tag;
    }
    
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    
    public String getResult()
    {
        return result;
    }
    
    public void setResult(String result)
    {
        this.result = result;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
