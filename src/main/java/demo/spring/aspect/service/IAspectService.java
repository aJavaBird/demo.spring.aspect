package demo.spring.aspect.service;

public interface IAspectService {
    /** 累加 */
    public long addFromTo(long start, long end);

    /** 除法，返回 a/b的计算值 */
    public long divide(long a, long b);
}
