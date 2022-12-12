package facecat.topin.swing;

/*
* 证券数据
*/
public class SecurityData {
        /*
        * 收盘价
        */
        public double m_close;

        /*
        * 日期
        */
        public double m_date;

        /*
        * 最高价
        */
        public double m_high;

        /*
        * 最低价
        */
        public double m_low;

        /*
        * 开盘价
        */
        public double m_open;

        /*
        * 成交量
        */
        public double m_volume;

        /*
        * 成交额
        */
        public double m_amount;

        /*
        * 复制数据
        */
        public void copy(SecurityData data) {
            m_close = data.m_close;
            m_date = data.m_date;
            m_high = data.m_high;
            m_low = data.m_low;
            m_open = data.m_open;
            m_volume = data.m_volume;
            m_amount = data.m_amount;
        }
}
