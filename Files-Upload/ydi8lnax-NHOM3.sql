with find_company(select * 
from company cpn 
where cpn.name like '%2%' 
      and  cpn.email  like '%1%'   
      and cpn.tax_code   like '%1%'  
      and cpn.number_staff   like '%1%')


