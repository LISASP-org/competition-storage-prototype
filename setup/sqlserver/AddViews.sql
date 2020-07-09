CREATE VIEW [dbo].[view_domain_event_entry] AS
SELECT [global_index]
      ,[event_identifier]
	  ,CAST([meta_data] as VARCHAR(MAX)) as meta_data
      ,CAST([payload] as VARCHAR(MAX)) as payload
      ,[payload_revision]
      ,[payload_type]
      ,[time_stamp]
      ,[aggregate_identifier]
      ,[sequence_number]
      ,[type]
  FROM [dbo].[domain_event_entry];
GO