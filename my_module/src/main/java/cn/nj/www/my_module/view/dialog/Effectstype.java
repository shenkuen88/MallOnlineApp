package cn.nj.www.my_module.view.dialog;

public enum Effectstype
{

    Fall(Fall.class), Newspager(NewsPaper.class);

    private Class<? extends BaseEffects> effectsClazz;

    private Effectstype(Class<? extends BaseEffects> mclass)
    {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator()
    {
        BaseEffects bEffects = null;
        try
        {
            bEffects = effectsClazz.newInstance();
        } catch (ClassCastException e)
        {
            throw new Error("Can not init animatorClazz instance");
        } catch (InstantiationException e)
        {
            throw new Error("Can not init animatorClazz instance");
        } catch (IllegalAccessException e)
        {
            throw new Error("Can not init animatorClazz instance");
        }
        return bEffects;
    }
}
